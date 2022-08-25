package datart.server.config;

import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.JWTParser;
import org.jeecg.modules.jmreport.api.JmReportTokenServiceI;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.Map;

@Component
public class JimuReportTokenService implements JmReportTokenServiceI {
    @Override
    public String getToken(HttpServletRequest request) {
        return JmReportTokenServiceI.super.getToken(request);
    }

    @Override
    public String getToken() {
        return JmReportTokenServiceI.super.getToken();
    }

    @Override
    public String getUsername(String s) {
        try {
            JWTClaimsSet jwtClaimSet = JWTParser.parse(s).getJWTClaimsSet();
            return (String) jwtClaimSet.getClaims().get("sub");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Boolean verifyToken(String s) {
        return true;
    }

    @Override
    public Map<String, Object> getUserInfo(String token) {
        return JmReportTokenServiceI.super.getUserInfo(token);
    }

    @Override
    public HttpHeaders customApiHeader() {
        return JmReportTokenServiceI.super.customApiHeader();
    }
}
