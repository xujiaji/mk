package com.github.xujiaji.mk.user.front.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author jiajixu
 * @date 2020/8/26 10:30
 */
@NoArgsConstructor
@Data
public class AppleKeys {

    private List<Keys> keys;

    @NoArgsConstructor
    @Data
    public static class Keys {
        /**
         * kty : RSA
         * kid : 86D88Kf
         * use : sig
         * alg : RS256
         * n : iGaLqP6y-SJCCBq5Hv6pGDbG_SQ11MNjH7rWHcCFYz4hGwHC4lcSurTlV8u3avoVNM8jXevG1Iu1SY11qInqUvjJur--hghr1b56OPJu6H1iKulSxGjEIyDP6c5BdE1uwprYyr4IO9th8fOwCPygjLFrh44XEGbDIFeImwvBAGOhmMB2AD1n1KviyNsH0bEB7phQtiLk-ILjv1bORSRl8AK677-1T8isGfHKXGZ_ZGtStDe7Lu0Ihp8zoUt59kx2o9uWpROkzF56ypresiIl4WprClRCjz8x6cPZXU2qNWhu71TQvUFwvIvbkE1oYaJMb0jcOTmBRZA2QuYw-zHLwQ
         * e : AQAB
         */

        private String kty;
        private String kid;
        private String use;
        private String alg;
        private String n;
        private String e;
    }
}
