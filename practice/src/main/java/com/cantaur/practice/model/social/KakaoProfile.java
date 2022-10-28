package com.cantaur.practice.model.social;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigInteger;

@Getter
@Setter
@NoArgsConstructor
public class KakaoProfile {
    private BigInteger id;
    private String connected_at;
    private Properties properties;
    private KakaoAccount kakao_account;
    private String accessToken;
    private String refreshToken;
    private int refreshTokenExpiresIn;
    private String providerCode;
    private String email;

    @Getter
    public static class Properties {
        private String nickname;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class KakaoAccount {
        private Profile profile;
        private Boolean profile_nickname_needs_agreement;
        private String email;
        private Boolean email_needs_agreement;
        private Boolean has_email;
        private Boolean is_email_valid;
        private Boolean is_email_verified;
        private Boolean has_age_range;
        private Boolean age_range_needs_agreement;
        private Boolean has_birthday;
        private Boolean birthday_needs_agreement;
        private String birthday;
        private String birthday_type;
        private Boolean has_gender;
        private Boolean gender_needs_agreement;

        @Getter
        public static class Profile {
            private String nickname;
        }
    }


}