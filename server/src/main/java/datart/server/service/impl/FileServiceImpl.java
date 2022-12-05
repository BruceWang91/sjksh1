/*
 * Datart
 * <p>
 * Copyright 2021
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package datart.server.service.impl;

import datart.core.base.consts.Const;
import datart.core.base.consts.FileOwner;
import datart.core.base.exception.Exceptions;
import datart.core.common.Application;
import datart.core.common.FileUtils;
import datart.core.entity.*;
import datart.core.mappers.FileSaveStreamMapper;
import datart.server.common.BinUtil;
import datart.server.common.UniversalExcelReaderUtil;
import datart.server.enums.WhetherEnum;
import datart.server.service.*;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;

@Service
public class FileServiceImpl extends BaseService implements FileService {

    @Autowired
    private IFileMainService fileMainService;
    @Autowired
    private IFileSheetsService fileSheetsService;
    @Autowired
    private IFileSheetFieldService fileSheetFieldService;
    @Autowired
    private IFileSaveService fileSaveService;
    @Autowired
    private FileSaveStreamMapper fileSaveStreamMapper;
    @Value("${spring.libreoffice.officehome}") // 从application.yml配置文件中获取
    private String home; // libreoffice安装位置
    @Value("${spring.upload}")
    private String localurl;

    @Override
    public String uploadFile(FileOwner fileOwner, String ownerId, MultipartFile file, String fileName) throws IOException {
        switch (fileOwner) {
            case DASHBOARD:
            case DATACHART:
                return saveVizImage(fileOwner, ownerId, file, fileName);
            case USER_AVATAR:
                return updateUserAvatar(ownerId, file);
            case ORG_AVATAR:
                return updateOrgAvatar(ownerId, file);
            case DATA_SOURCE:
                return saveAsDatasource(fileOwner, ownerId, file);
            case FILE_DATA:
                return saveFileData(fileOwner, ownerId, file);
            default:
                Exceptions.msg("unknown file type " + fileOwner);
        }
        return null;
    }

    @Override
    public boolean deleteFiles(FileOwner fileOwner, String ownerId) {
        try {
            switch (fileOwner) {
                case ORG_AVATAR:
                    securityManager.requireOrgOwner(ownerId);
                    OrgService orgService = Application.getBean(OrgService.class);
                    orgService.updateAvatar(ownerId, "");
                    break;
                case USER_AVATAR:
                    requireExists(ownerId, User.class);
                    UserService userService = Application.getBean(UserService.class);
                    userService.updateAvatar("");
                    break;
                default:
                    break;
            }
            String path = FileUtils.concatPath(Application.getFileBasePath(), fileOwner.getPath(), ownerId);
            return FileSystemUtils.deleteRecursively(new File(path));
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public String getBasePath(FileOwner owner, String id) {
        return FileUtils.concatPath(Application.getFileBasePath(), owner.getPath(), id);
    }

    private String saveVizImage(FileOwner fileOwner, String ownerId, MultipartFile file, String fileName) throws IOException {
        switch (fileOwner) {
            case DASHBOARD:
                requireExists(ownerId, Dashboard.class);
                break;
            case DATACHART:
                requireExists(ownerId, Datachart.class);
                break;
        }
        String filePath = FileUtils.concatPath(fileOwner.getPath(), ownerId, StringUtils.isBlank(fileName) ? file.getOriginalFilename() : fileName);
        String fullPath = FileUtils.withBasePath(filePath);
        FileUtils.mkdirParentIfNotExist(fullPath);
        file.transferTo(new File(fullPath));
        return filePath;
    }

    private String updateUserAvatar(String userId, MultipartFile file) throws IOException {

        requireExists(userId, User.class);

        String filePath = FileUtils.concatPath(FileOwner.USER_AVATAR.getPath(), userId, file.getOriginalFilename());

        String fullPath = FileUtils.withBasePath(filePath);

        FileUtils.mkdirParentIfNotExist(fullPath);

        Thumbnails.of(file.getInputStream()).size(Const.IMAGE_WIDTH, Const.IMAGE_HEIGHT).toFile(fullPath);

        UserService userService = Application.getBean(UserService.class);

        userService.updateAvatar(filePath);

        return filePath;
    }

    private String updateOrgAvatar(String orgId, MultipartFile file) throws IOException {

        requireExists(orgId, Organization.class);

        String filePath = FileUtils.concatPath(FileOwner.ORG_AVATAR.getPath(), orgId, file.getOriginalFilename());

        String fullPath = FileUtils.withBasePath(filePath);


        FileUtils.mkdirParentIfNotExist(fullPath);

        Thumbnails.of(file.getInputStream()).size(Const.IMAGE_WIDTH, Const.IMAGE_HEIGHT).toFile(fullPath);

        OrgService orgService = Application.getBean(OrgService.class);

        orgService.updateAvatar(orgId, filePath);

        return filePath;
    }

    public String saveAsDatasource(FileOwner fileOwner, String ownerId, MultipartFile file) throws IOException {

        requireExists(ownerId, Source.class);

        String filePath = FileUtils.concatPath(fileOwner.getPath(), ownerId, System.currentTimeMillis() + "-" + file.getOriginalFilename());

        String fullPath = FileUtils.withBasePath(filePath);

        FileUtils.mkdirParentIfNotExist(fullPath);

        file.transferTo(new File(fullPath));

        return filePath;
    }

    public String saveFileData(FileOwner fileOwner, String ownerId, MultipartFile file) throws IOException {

        Long fileId = Long.valueOf(ownerId);
        FileMain fileMain = fileMainService.selectFileMainByFileId(fileId);
        List<FileSheets> fileSheetsList = fileSheetsService.selectFileSheetsList(new FileSheets() {{
            setFileId(fileMain.getFileId());
            setDelFlag(WhetherEnum.NO.getValue().toString());
        }});
        // 获取excel数据层级为sheet、row、cell
        List<List<Map<String, Object>>> list = UniversalExcelReaderUtil.getDataFromExcel(file, fileSheetsList);
        HashMap<String, Object> importMap = new HashMap<>();
        importMap.put("fileId", fileId);
        // 遍历sheet
        for (int i = 0; i < list.size(); i++) {
            List<HashMap<String, Object>> objectList = new ArrayList<>();
            String domainname = "";
            for (FileSheets fileSheets : fileSheetsList) {
                // fileSheets的orderNum为该文件的第几个sheet从0开始
                if (fileSheets.getOrderNum() == i) {
                    // 查询该sheet下表格列字段与数据库对应
                    List<FileSheetField> fileSheetFieldList = fileSheetFieldService.selectFileSheetFieldList(new FileSheetField() {{
                        setSheetId(fileSheets.getSheetId());
                        setStatus(WhetherEnum.YES.getValue().toString());
                    }});
                    // 获取到的excel的sheet(i)数据行遍历
                    for (Map<String, Object> map : list.get(i)) {
                        boolean whetherImport = true;
                        HashMap<String, Object> hashMap = new HashMap<>();
                        for (FileSheetField fileSheetField : fileSheetFieldList) {

                            if (WhetherEnum.YES.getValue().toString().equals(fileSheetField.getImportFlag()) &&
                                    StringUtils.isBlank(map.get(fileSheetField.getCellNum() + "").toString())) {

                                whetherImport = false;
                                continue;
                            }
                            if (null != map.get(fileSheetField.getCellNum() + "")) {
                                hashMap.put(fileSheetField.getEntityField(), map.get(fileSheetField.getCellNum() + "").toString());
                            } else {
                                hashMap.put(fileSheetField.getEntityField(), "");
                            }
                        }
                        if (!whetherImport) {
                            continue;
                        }
                        objectList.add(hashMap);
                    }
                    domainname = fileSheets.getEntityName();
                }
            }
            importMap.put(domainname, objectList);
        }
        fileMainService.splitTableImport(importMap);

//        String filePath = FileUtils.concatPath(fileOwner.getPath(), ownerId, System.currentTimeMillis() + "-" + file.getOriginalFilename());
//
//        String fullPath = FileUtils.withBasePath(filePath);
//
//        FileUtils.mkdirParentIfNotExist(fullPath);
//
//        file.transferTo(new File(fullPath));

        return "success";
    }

    @Override
    public HashMap<String, Object> uploadtopdf(FileOwner fileOwner, Long classId, Long parentId, String orgId, MultipartFile file) throws IOException {


        String filePath = FileUtils.concatPath(fileOwner.getPath(), classId.toString(), System.currentTimeMillis() + "-" + file.getOriginalFilename());

        String fullPath = FileUtils.withBasePath(filePath);

        FileUtils.mkdirParentIfNotExist(fullPath);

        // 上传并返回新文件名称
        HashMap<String, String> fileName = FileUtils.uploadtopdf(home, fullPath, file);
        String url = fileName.get("filename");
        byte[] fileStream = BinUtil.fileToBinArray(new File(url));
        String newurl = url.replace(localurl, "/upload/");
        String pdfurl = fileName.get("pdfname");
        if (StringUtils.isBlank(pdfurl)) {
            FileSave fileSave = new FileSave();
            fileSave.setClassId(classId);
            fileSave.setParentId(parentId);
            fileSave.setOrgId(orgId);
            fileSave.setFileName(file.getOriginalFilename());
            fileSave.setNewName(FileUtils.getName(fileName.get("filename")));
            fileSave.setUrl(newurl);
            fileSave.setCreateBy(getCurrentUser().getId());
            fileSave.setCreateTime(new Date());
            fileSaveService.insertFileSave(fileSave);
            FileSaveStream fileSaveStream = new FileSaveStream();
            fileSaveStream.setFileSaveId(fileSave.getId());
            fileSaveStream.setFileStream(fileStream);
            fileSaveStreamMapper.insertFileSaveStream(fileSaveStream);
            HashMap map = new HashMap();
            map.put("url", url);
            map.put("fileName", fileName.get("filename"));
            map.put("newFileName", FileUtils.getName(fileName.get("filename")));
            map.put("originalFilename", file.getOriginalFilename());
            return map;
        }
        byte[] pdfStream = BinUtil.fileToBinArray(new File(pdfurl));
        String newpdfurl = pdfurl.replace(localurl, "/upload/");
        FileSave fileSave = new FileSave();
        fileSave.setClassId(classId);
        fileSave.setParentId(parentId);
        fileSave.setOrgId(orgId);
        fileSave.setFileName(file.getOriginalFilename());
        fileSave.setNewName(FileUtils.getName(fileName.get("filename")));
        fileSave.setPdfName(FileUtils.getName(fileName.get("pdfname")));
        fileSave.setUrl(newurl);
        fileSave.setPdfurl(newpdfurl);
        fileSave.setCreateBy(getCurrentUser().getId());
        fileSave.setCreateTime(new Date());
        fileSaveService.insertFileSave(fileSave);
        FileSaveStream fileSaveStream = new FileSaveStream();
        fileSaveStream.setFileSaveId(fileSave.getId());
        fileSaveStream.setFileStream(fileStream);
        fileSaveStream.setPdfStream(pdfStream);
        fileSaveStreamMapper.insertFileSaveStream(fileSaveStream);
        HashMap map = new HashMap();
        map.put("url", url);
        map.put("pdfurl", pdfurl);
        map.put("fileName", fileName.get("filename"));
        map.put("pdfname", fileName.get("pdfname"));
        map.put("newFileName", FileUtils.getName(fileName.get("filename")));
        map.put("pdfFileName", FileUtils.getName(fileName.get("pdfname")));
        map.put("originalFilename", file.getOriginalFilename());
        return map;
    }

    @Override
    public HashMap<String, Object> uploadstaticfile(FileOwner fileOwner, Long parentId, MultipartFile file) throws IOException {


        String filePath = FileUtils.concatPath(fileOwner.getPath(), parentId.toString(), System.currentTimeMillis() + "-" + file.getOriginalFilename());

        String fullPath = FileUtils.withBasePath(filePath);

        FileUtils.mkdirParentIfNotExist(fullPath);

        file.transferTo(new File(fullPath));

        byte[] fileStream = BinUtil.fileToBinArray(new File(fullPath));
        String newurl = fullPath.replace(localurl, "/upload/");
        HashMap map = new HashMap();
        map.put("url", newurl);
        map.put("fileName", fullPath);
        map.put("fileStream", fileStream);
        return map;
    }
}
