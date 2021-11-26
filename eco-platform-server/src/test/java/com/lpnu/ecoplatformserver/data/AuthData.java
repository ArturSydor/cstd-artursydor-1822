package com.lpnu.ecoplatformserver.data;

import com.lpnu.ecoplatformserver.user.dto.UserLoginDto;

import static com.lpnu.ecoplatformserver.data.CommonTestConstants.MANAGER_USER_EMAIL;
import static com.lpnu.ecoplatformserver.data.CommonTestConstants.MANAGER_USER_PASSWORD;

public final class AuthData {

    public static final UserLoginDto LOGIN_REQUEST_FOR_MANAGER = new UserLoginDto(
            MANAGER_USER_EMAIL,
            MANAGER_USER_PASSWORD
    );

}
