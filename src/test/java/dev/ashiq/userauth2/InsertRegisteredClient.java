package dev.ashiq.userauth2;

//@SpringBootTest
// class InsertRegisteredClientTest {
//    @Autowired
//    private PasswordEncoder passwordEncoder;
//
//    @Autowired
//    private JpaRegisteredClientRepository jpaRegisteredClientRepository;
//
//    @Test
//    @Commit
 //   public void insertNewClientToDb() {
//        RegisteredClient oidcClient = RegisteredClient.withId(UUID.randomUUID().toString())
//                .clientId("postman")
//                .clientSecret(passwordEncoder.encode("postmanpassword"))
//                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
//                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
//                .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
//                .redirectUri("https://oauth.pstmn.io/v1/callback")
//                .postLogoutRedirectUri("http://127.0.0.1:8080/")
//                .scope(OidcScopes.OPENID)
//                .scope(OidcScopes.PROFILE)
//                .clientSettings(ClientSettings.builder().requireAuthorizationConsent(true).build())
//                .build();
//
//        jpaRegisteredClientRepository.save(oidcClient);
//    }
//}
