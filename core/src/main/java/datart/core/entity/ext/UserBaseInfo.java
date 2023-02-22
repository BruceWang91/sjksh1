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

package datart.core.entity.ext;

import datart.core.entity.User;
import lombok.Data;


@Data
public class UserBaseInfo {

    private String id;

    private String email;

    private String username;

    private String avatar;

    private String name;

    private String description;

    private boolean orgOwner;

    private Long deptId;

    private String adminCompetence;

    private String deptName;

    public UserBaseInfo() {
    }

    public UserBaseInfo(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.username = user.getUsername();
        this.name = user.getName();
        this.description = user.getDescription();
        this.avatar = user.getAvatar();
        this.deptId = user.getDeptId();
        this.deptName = user.getDeptName();
        this.adminCompetence = user.getAdminCompetence();
    }
}
