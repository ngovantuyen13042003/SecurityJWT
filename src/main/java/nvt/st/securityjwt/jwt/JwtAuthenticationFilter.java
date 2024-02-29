package nvt.st.securityjwt.jwt;

import jakarta.annotation.Nonnull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import nvt.st.securityjwt.constants.SecurityConstants;
import nvt.st.securityjwt.security.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private CustomUserDetailsService userDetailsService;


    // get token from request
    private String getTokenFromRequest(HttpServletRequest request){
        // Authorization: Bearer + token
        String bearerToken = request.getHeader("Authorization");

        // Authorization: Bearer + token
        // start index 7:  due to  Bearer 6 ký tự + 1 ký tự khoảng trắng
        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")){
            return bearerToken.substring(7);
        }
        return null;
    }

    @Override
    protected void doFilterInternal(
            @Nonnull HttpServletRequest request,
            @Nonnull HttpServletResponse response,
            @Nonnull FilterChain filterChain)
            throws ServletException, IOException {

//        String authHeader = request.getHeader(SecurityConstants.AUTHORIZATION);

//        if(authHeader != null || !authHeader.startsWith(SecurityConstants.BEARER)) {
//            filterChain.doFilter(request, response);
//            return;
//        }

        // Authorization: Bearer + token
        // start index 7:  due to  Bearer 6 ký tự + 1 ký tự khoảng trắng
//        String token = authHeader.substring(7);


        String token = getTokenFromRequest(request);
        // get username
        String username =  jwtTokenProvider.extractUsername(token);
        // check user authenticated and validate
        if(username !=  null && SecurityContextHolder.getContext().getAuthentication() == null && jwtTokenProvider.validateJwtToken(token)) {

            // load UserDetails
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            if(jwtTokenProvider.isValid(token, userDetails)){
                // use info of UserDetails to create UsernamePasswordAuthenticationToken
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,  // Principal: Thông tin chi tiết về người dùng (UserDetails)
                        null,         // Credentials: Mật khẩu hoặc token xác thực
                        userDetails.getAuthorities() // Authorities: Quyền hạn hoặc vai trò của người dùng
                );

                // Thiết lập chi tiết (Details) của yêu cầu xác thực
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                // update SecurityContextHolder với đối tượng Authentication mới tạo
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        filterChain.doFilter(request, response);
    }


    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return request.getServletPath().startsWith("/api/auth/");
    }
}











