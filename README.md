<h2>Spring Boot 2 JWT Authentication with Spring Security</h2><br>Lược&nbsp;đồ Spring Security/JWT&nbsp;chia thành 3 lớp: 
<ol>
<li>HTTP</li>
<li>Spring Security</li>
<li>REST API.</li></ol><br><img src="https://s3-ap-southeast-1.amazonaws.com/848b5e3eb7cd78a56a3a8361fff6bd4c/1703079824spring-boot-jwt-mysql-spring-security-architecture.png" width="800" height="404"><br>
<ul>
<li><strong>SecurityContextHolder</strong>&nbsp;cung&nbsp;cấp quyền truy cập&nbsp;đến&nbsp;the <strong>SecurityContext</strong>.</li>
<li><strong>SecurityContext</strong> nắm giữ việc Xác thực <strong><em>Authentication</em></strong>&nbsp;và những thông tin bảo mật&nbsp;riêng cho các request.</li>
<li><strong>Authenticatio</strong>n&nbsp;đại diện cho&nbsp;<strong>principal </strong>(người dùng), bao gồm<strong> GrantedAuthority</strong> đại diện cho các quyền&nbsp;được cấp trên phạm vi ứng dụng&nbsp;cho principal.</li>
<li><strong>UserDetails</strong> chứa các thông tin cần thiết để build&nbsp;đối tượng&nbsp;<strong>Authentication</strong>&nbsp;từ DAOs&nbsp;hoặc&nbsp;nguồn khác của&nbsp;security data.</li>
<li><strong>UserDetailsService</strong> giúp tạo <strong>UserDetails</strong>&nbsp;từ&nbsp;username&nbsp;và thường&nbsp;được sử dụng bởi&nbsp;AuthenticationProvider.</li>
<li><strong>JwtAuthTokenFilter</strong> (kế thừa&nbsp;<strong>OncePerRequestFilter</strong>)&nbsp;tiền xử lý&nbsp;HTTP request,&nbsp;từ <strong>Token</strong>,&nbsp;tạo <strong>Authentication</strong>&nbsp;rồi&nbsp;đưa vào&nbsp;<strong>SecurityContext</strong>.</li>
<li><strong>JwtProvider</strong> validates, parses chuỗi&nbsp;token hoặc generates chuỗi&nbsp;token từ <strong>UserDetails</strong>.</li>
<li><strong>UsernamePasswordAuthenticationToken</strong> gets username/password&nbsp;từ <em><strong>login Request</strong></em>&nbsp;rồi&nbsp;kết hợp thành thể hiện của&nbsp;<strong>Authentication</strong> interface.</li>
<li><strong>AuthenticationManager</strong>&nbsp;sử dụng&nbsp;<strong>DaoAuthenticationProvider</strong> (với&nbsp;sự&nbsp;hỗ trợ của&nbsp;<strong>UserDetailsService</strong> &amp; <strong>PasswordEncoder</strong>)&nbsp;để validate&nbsp;đối tượng&nbsp;<strong>UsernamePasswordAuthenticationToken</strong>. Sau đó trả về một đối tượng <strong>Authentication</strong> với đầy đủ thông tin sau khi xác thực thành công.</li>
<li><strong>SecurityContext</strong> được thiết lập bằng cách&nbsp;gọi <strong>SecurityContextHolder.getContext().setAuthentication(​)</strong>&nbsp;với&nbsp;đối tượng&nbsp;<strong>Authentication</strong> được return ở trên.</li>
<li><strong>AuthenticationEntryPoint</strong> handles AuthenticationException.</li>
<li>Truy cập tới <strong>Restful API</strong>&nbsp;được bảo vệ bằng <strong>HTTPSecurity</strong>&nbsp;và phải được xác thực bằng <strong>Method Security Expressions</strong> (<strong>@EnableMethodSecurity</strong> từ Spring boot 3).</li></ul>
<h2>Ví dụ luồng của Spring Boot Login&nbsp;và Registration với Access Token &amp; Refresh Token</h2>Refresh Token&nbsp;sẽ được cung cấp trong HttpOnly Cookie tại thời điểm người dùng&nbsp;login thành công.<br>JWT Token&nbsp;hợp lệ phải được lưu trữ trong HttpOnly Cookie nếu client truy cập các tài nguyên được bảo vệ.<br><br><br><img src="https://s3-ap-southeast-1.amazonaws.com/848b5e3eb7cd78a56a3a8361fff6bd4c/1703082527spring-security-refresh-token-jwt-spring-boot-flow.png"><br><br>
