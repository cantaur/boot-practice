
package com.cantaur.practice.config.resources;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class KakaoSocial {
    private String clientId;

    private String redirectUrl;
}
