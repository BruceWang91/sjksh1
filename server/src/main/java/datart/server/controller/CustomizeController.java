package datart.server.controller;

import com.alibaba.fastjson.JSONArray;
import datart.core.base.annotations.SkipLogin;
import datart.core.mappers.CustomizeMapper;
import datart.server.base.dto.ResponseData;
import datart.server.service.ICustomizeService;
import datart.server.service.IToolsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 自定义开放接口Controller
 *
 * @author wangya
 * @date 2022-10-01
 */
@Api("自定义开放接口")
@RestController
@RequestMapping(value = "/cystomize")
public class CustomizeController extends BaseController {

    @Autowired
    private IToolsService toolsService;
    @Autowired
    private CustomizeMapper customizeMapper;
    @Autowired
    private ICustomizeService customizeService;

    @ApiOperation("华夏产业主指标")
    @GetMapping(value = "hxZhuZhiBiao", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @SkipLogin
    public ResponseData<JSONArray> hxZhuZhiBiao(@ApiParam("企业名称") @RequestParam(value = "qymc", required = false) String qymc) {

        List<HashMap<String, Object>> list = new ArrayList<>();
        HashMap<String, Object> map = new HashMap<>();
        map.put("zcze", "资产总额");
        map.put("zczelist", "[1,2,3,4]");
        map.put("yyzsr", "营业总收入");
        map.put("yyzsrlist", "[1,2,3,4]");
        map.put("lrze", "利润总额");
        map.put("lrzelist", "[1,2,3,4]");
        map.put("nd", "年度");
        list.add(map);
        JSONArray jsonArray = toolsService.mapListToJsonArray(list);
        return ResponseData.success(jsonArray);
    }

    @ApiOperation("华夏产业近三年营业收入")
    @GetMapping(value = "hxJinSanNianyysr", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @SkipLogin
    public ResponseData<JSONArray> hxJinSanNianyysr(@ApiParam("企业名称") @RequestParam(value = "qymc", required = false) String qymc) {

        List<HashMap<String, Object>> list = new ArrayList<>();
        HashMap<String, Object> map = new HashMap<>();
        map.put("bb", "本部");
        map.put("sg", "省管");
        map.put("kg", "控股");
        map.put("cg", "参股");
        map.put("nd", "年度");
        list.add(map);
        JSONArray jsonArray = toolsService.mapListToJsonArray(list);
        return ResponseData.success(jsonArray);
    }

    @ApiOperation("华夏产业近三年利润总额")
    @GetMapping(value = "hxJinSanNianlrze", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @SkipLogin
    public ResponseData<JSONArray> hxJinSanNianlrze(@ApiParam("企业名称") @RequestParam(value = "qymc", required = false) String qymc) {

        List<HashMap<String, Object>> list = new ArrayList<>();
        HashMap<String, Object> map = new HashMap<>();
        map.put("bb", "本部");
        map.put("sg", "省管");
        map.put("kg", "控股");
        map.put("cg", "参股");
        map.put("nd", "年度");
        list.add(map);
        JSONArray jsonArray = toolsService.mapListToJsonArray(list);
        return ResponseData.success(jsonArray);
    }

    @ApiOperation("华夏产业近三年投资收益")
    @GetMapping(value = "hxJinSanNiantzsy", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @SkipLogin
    public ResponseData<JSONArray> hxJinSanNiantzsy(@ApiParam("企业名称") @RequestParam(value = "qymc", required = false) String qymc) {

        List<HashMap<String, Object>> list = new ArrayList<>();
        HashMap<String, Object> map = new HashMap<>();
        map.put("bb", "本部");
        map.put("sg", "省管");
        map.put("kg", "控股");
        map.put("cg", "参股");
        map.put("nd", "年度");
        list.add(map);
        JSONArray jsonArray = toolsService.mapListToJsonArray(list);
        return ResponseData.success(jsonArray);
    }

    @ApiOperation("省管产业当年营收")
    @GetMapping(value = "sgcyDangNianYingShou", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @SkipLogin
    public ResponseData<JSONArray> sgcyDangNianYingShou() {

        List<HashMap<String, Object>> list = new ArrayList<>();
        HashMap<String, Object> map = new HashMap<>();
        map.put("xtwsr", "系统外收入");
        map.put("xtnsr", "系统内收入");
        map.put("nd", "年度");
        list.add(map);
        JSONArray jsonArray = toolsService.mapListToJsonArray(list);
        return ResponseData.success(jsonArray);
    }

    @ApiOperation("省管产业主指标")
    @GetMapping(value = "sgcyZhuZhiBiao", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @SkipLogin
    public ResponseData<JSONArray> sgcyZhuZhiBiao(@ApiParam("企业名称") @RequestParam(value = "qymc", required = false) String qymc) {

        List<HashMap<String, Object>> list = new ArrayList<>();
        HashMap<String, Object> map = new HashMap<>();
        map.put("yyzsr", "营业总收入");
        map.put("yyzsrwcl", "营业总收入完成度");
        map.put("lrze", "利润总额");
        map.put("lrzewcl", "利润总额完成度");
        map.put("zcfzl", "资产负债率（%）");
        map.put("zcfzlwcl", "资产负债率完成度");
        map.put("jzcsyl", "净资产收益率（%）");
        map.put("jzcsylwcl", "净资产收益率完成度");
        map.put("yszkhsl", "应收账款回收率（%）");
        map.put("yszkhslwcl", "应收账款回收率完成度");
        map.put("zcze", "资产总额");
        map.put("zczewcl", "资产总额完成度");
        map.put("nd", "年度");
        list.add(map);
        JSONArray jsonArray = toolsService.mapListToJsonArray(list);
        return ResponseData.success(jsonArray);
    }

    @ApiOperation("省管产业近三年营业收入")
    @GetMapping(value = "sgcyJinSanNianyysr", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @SkipLogin
    public ResponseData<JSONArray> sgcyJinSanNianyysr(@ApiParam("企业名称") @RequestParam(value = "qymc", required = false) String qymc) {

        List<HashMap<String, Object>> list = new ArrayList<>();
        HashMap<String, Object> map = new HashMap<>();
        map.put("bkD", "板块D");
        map.put("bkC", "板块C");
        map.put("bkB", "板块B");
        map.put("bkA", "板块A");
        map.put("nd", "年度");
        list.add(map);
        JSONArray jsonArray = toolsService.mapListToJsonArray(list);
        return ResponseData.success(jsonArray);
    }

    @ApiOperation("省管产业近三年利润总额")
    @GetMapping(value = "sgcyJinSanNianlrze", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @SkipLogin
    public ResponseData<JSONArray> sgcyJinSanNianlrze(@ApiParam("企业名称") @RequestParam(value = "qymc", required = false) String qymc) {

        List<HashMap<String, Object>> list = new ArrayList<>();
        HashMap<String, Object> map = new HashMap<>();
        map.put("bkD", "板块D");
        map.put("bkC", "板块C");
        map.put("bkB", "板块B");
        map.put("bkA", "板块A");
        map.put("nd", "年度");
        list.add(map);
        JSONArray jsonArray = toolsService.mapListToJsonArray(list);
        return ResponseData.success(jsonArray);
    }

    @ApiOperation("省管产业近三年分红情况")
    @GetMapping(value = "sgcyJinSanNianfhqk", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @SkipLogin
    public ResponseData<JSONArray> sgcyJinSanNianfhqk(@ApiParam("企业名称") @RequestParam(value = "qymc", required = false) String qymc) {

        List<HashMap<String, Object>> list = new ArrayList<>();
        HashMap<String, Object> map = new HashMap<>();
        map.put("bkD", "板块D");
        map.put("bkC", "板块C");
        map.put("bkB", "板块B");
        map.put("bkA", "板块A");
        map.put("nd", "年度");
        list.add(map);
        JSONArray jsonArray = toolsService.mapListToJsonArray(list);
        return ResponseData.success(jsonArray);
    }

    @ApiOperation("控股企业净资产收益")
    @GetMapping(value = "kgqyJingZiChanShouYi", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @SkipLogin
    public ResponseData<JSONArray> kgqyJingZiChanShouYi(@ApiParam("企业名称") @RequestParam(value = "qymc", required = false) String qymc) {

        List<HashMap<String, Object>> list = new ArrayList<>();
        HashMap<String, Object> map = new HashMap<>();
        map.put("jzcsy", "净资产收益");
        map.put("nd", "年度");
        list.add(map);
        JSONArray jsonArray = toolsService.mapListToJsonArray(list);
        return ResponseData.success(jsonArray);
    }

    @ApiOperation("控股企业历年分红1")
    @GetMapping(value = "kgqyLiNianFenHong1", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @SkipLogin
    public ResponseData<JSONArray> kgqyLiNianFenHong1(@ApiParam("企业名称") @RequestParam(value = "qymc", required = false) String qymc) {

        List<HashMap<String, Object>> list = new ArrayList<>();
        HashMap<String, Object> map = new HashMap<>();
        map.put("fhdzje", "分红到账金额");
        map.put("fhjyje", "分红决议金额");
        map.put("ysje", "应收金额");
        map.put("nd", "年度");
        list.add(map);
        JSONArray jsonArray = toolsService.mapListToJsonArray(list);
        return ResponseData.success(jsonArray);
    }

    @ApiOperation("控股企业历年分红2")
    @GetMapping(value = "kgqyLiNianFenHong2", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @SkipLogin
    public ResponseData<JSONArray> kgqyLiNianFenHong2(@ApiParam("企业名称") @RequestParam(value = "qymc", required = false) String qymc) {

        List<HashMap<String, Object>> list = new ArrayList<>();
        HashMap<String, Object> map = new HashMap<>();
        map.put("ljdzje", "累计到账金额");
        map.put("ljfhje", "累计分红金额");
        map.put("ljtzhbl", "累计投资回报率");
        map.put("pjtzhbl", "平均投资回报率");
        list.add(map);
        JSONArray jsonArray = toolsService.mapListToJsonArray(list);
        return ResponseData.success(jsonArray);
    }

    @ApiOperation("控股企业营业收入")
    @GetMapping(value = "kgqyYingYeShouRu", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @SkipLogin
    public ResponseData<JSONArray> kgqyYingYeShouRu(@ApiParam("企业名称") @RequestParam(value = "qymc", required = false) String qymc) {

        List<HashMap<String, Object>> list = new ArrayList<>();
        HashMap<String, Object> map = new HashMap<>();
        map.put("yysr", "营业收入");
        map.put("nd", "年度");
        list.add(map);
        JSONArray jsonArray = toolsService.mapListToJsonArray(list);
        return ResponseData.success(jsonArray);
    }

    @ApiOperation("控股企业净利润总额")
    @GetMapping(value = "kgqyJingLiRunZongE", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @SkipLogin
    public ResponseData<JSONArray> kgqyJingLiRunZongE(@ApiParam("企业名称") @RequestParam(value = "qymc", required = false) String qymc) {

        List<HashMap<String, Object>> list = new ArrayList<>();
        HashMap<String, Object> map = new HashMap<>();
        map.put("jlrze", "净利润总额");
        map.put("nd", "年度");
        list.add(map);
        JSONArray jsonArray = toolsService.mapListToJsonArray(list);
        return ResponseData.success(jsonArray);
    }

    @ApiOperation("控股企业近三年收入")
    @GetMapping(value = "kgqyJinSanNianShouRu", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @SkipLogin
    public ResponseData<JSONArray> kgqyJinSanNianShouRu(@ApiParam("企业名称") @RequestParam(value = "qymc", required = false) String qymc) {

        List<HashMap<String, Object>> list = new ArrayList<>();
        HashMap<String, Object> map = new HashMap<>();
        map.put("srje", "收入金额");
        map.put("nd", "年度");
        list.add(map);
        JSONArray jsonArray = toolsService.mapListToJsonArray(list);
        return ResponseData.success(jsonArray);
    }

    @ApiOperation("参股企业历年分红1")
    @GetMapping(value = "cgqyLiNianFenHong1", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @SkipLogin
    public ResponseData<JSONArray> cgqyLiNianFenHong1(@ApiParam("企业名称") @RequestParam(value = "qymc", required = false) String qymc) {

        List<HashMap<String, Object>> list = new ArrayList<>();
        HashMap<String, Object> map = new HashMap<>();
        map.put("fhdzje", "分红到账金额");
        map.put("fhjyje", "分红决议金额");
        map.put("ysje", "应收金额");
        map.put("nd", "年度");
        list.add(map);
        JSONArray jsonArray = toolsService.mapListToJsonArray(list);
        return ResponseData.success(jsonArray);
    }

    @ApiOperation("参股企业历年分红2")
    @GetMapping(value = "cgqyLiNianFenHong2", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @SkipLogin
    public ResponseData<JSONArray> cgqyLiNianFenHong2(@ApiParam("企业名称") @RequestParam(value = "qymc", required = false) String qymc) {

        List<HashMap<String, Object>> list = new ArrayList<>();
        HashMap<String, Object> map = new HashMap<>();
        map.put("ljdzje", "累计到账金额");
        map.put("ljfhje", "累计分红金额");
        map.put("ljtzhbl", "累计投资回报率");
        map.put("pjtzhbl", "平均投资回报率");
        list.add(map);
        JSONArray jsonArray = toolsService.mapListToJsonArray(list);
        return ResponseData.success(jsonArray);
    }

    @ApiOperation("参股企业营业收入")
    @GetMapping(value = "cgqyYingYeShouRu", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @SkipLogin
    public ResponseData<JSONArray> cgqyYingYeShouRu(@ApiParam("企业名称") @RequestParam(value = "qymc", required = false) String qymc) {

        List<HashMap<String, Object>> list = new ArrayList<>();
        HashMap<String, Object> map = new HashMap<>();
        map.put("yysr", "营业收入");
        map.put("nd", "年度");
        list.add(map);
        JSONArray jsonArray = toolsService.mapListToJsonArray(list);
        return ResponseData.success(jsonArray);
    }

    @ApiOperation("参股企业净利润总额")
    @GetMapping(value = "cgqyJingLiRunZongE", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @SkipLogin
    public ResponseData<JSONArray> cgqyJingLiRunZongE(@ApiParam("企业名称") @RequestParam(value = "qymc", required = false) String qymc) {

        List<HashMap<String, Object>> list = new ArrayList<>();
        HashMap<String, Object> map = new HashMap<>();
        map.put("jlrze", "净利润总额");
        map.put("nd", "年度");
        list.add(map);
        JSONArray jsonArray = toolsService.mapListToJsonArray(list);
        return ResponseData.success(jsonArray);
    }

    @ApiOperation("test")
    @GetMapping(value = "test")
    @SkipLogin
    public ResponseData<JSONArray> test(@ApiParam("企业名称") @RequestParam(value = "qymc", required = false) String qymc) {

        List<HashMap<String, Object>> list = customizeService.test(new HashMap<String, Object>() {{
            put("qymc", qymc);
        }});
        JSONArray jsonArray = toolsService.mapListToJsonArray(list);
        return ResponseData.success(jsonArray);
    }
}
