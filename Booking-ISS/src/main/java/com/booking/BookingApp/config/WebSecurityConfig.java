package com.booking.BookingApp.config;


import com.booking.BookingApp.security.auth.RestAuthenticationEntryPoint;
import com.booking.BookingApp.security.auth.TokenAuthenticationFilter;
import com.booking.BookingApp.service.CustomUserDetailsService;
import com.booking.BookingApp.util.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.context.RequestAttributeSecurityContextRepository;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;

import static org.springframework.security.config.http.MatcherType.mvc;


@Configuration
// Injektovanje bean-a za bezbednost
@EnableWebSecurity
//Ukljucivanje podrske za anotacije "@Pre*" i "@Post*" koje ce aktivirati autorizacione provere za svaki pristup metodi
@EnableMethodSecurity

@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig {

    //Podesavanje CORSa, svi zahtevi sa http://localhost:4200 se propustaju
    //Podesavanja CORS-a
    //https://docs.spring.io/spring-security/reference/servlet/integrations/cors.html
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:4200"));
        configuration.setAllowedMethods(Arrays.asList("POST", "PUT", "GET", "OPTIONS", "DELETE", "PATCH")); // or simply "*"
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
    // Servis koji se koristi za citanje podataka o korisnicima aplikacije
    @Bean
    public UserDetailsService userDetailsService() {
        return new CustomUserDetailsService();
    }

    // Implementacija PasswordEncoder-a koriscenjem BCrypt hashing funkcije.
    // BCrypt po defalt-u radi 10 rundi hesiranja prosledjene vrednosti.
//    @Bean
//    public BCryptPasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }


    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        // 1. koji servis da koristi da izvuce podatke o korisniku koji zeli da se autentifikuje
        // prilikom autentifikacije, AuthenticationManager ce sam pozivati loadUserByUsername() metodu ovog servisa
        authProvider.setUserDetailsService(userDetailsService());
        // 2. kroz koji enkoder da provuce lozinku koju je dobio od klijenta u zahtevu
        // da bi adekvatan hash koji dobije kao rezultat hash algoritma uporedio sa onim koji se nalazi u bazi (posto se u bazi ne cuva plain lozinka)
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

    // Handler za vracanje 401 kada klijent sa neodogovarajucim korisnickim imenom i lozinkom pokusa da pristupi resursu
    @Autowired
    private RestAuthenticationEntryPoint restAuthenticationEntryPoint;


    // Registrujemo authentication manager koji ce da uradi autentifikaciju korisnika za nas
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    // Injektujemo implementaciju iz TokenUtils klase kako bismo mogli da koristimo njene metode za rad sa JWT u TokenAuthenticationFilteru
    @Autowired
    private TokenUtils tokenUtils;


    // Definisemo prava pristupa za zahteve ka odredjenim URL-ovima/rutama
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.securityContext((securityContext) -> securityContext
                .securityContextRepository(new RequestAttributeSecurityContextRepository())
        );

        http.cors(Customizer.withDefaults());

        // zbog jednostavnosti primera ne koristimo Anti-CSRF token (https://cheatsheetseries.owasp.org/cheatsheets/Cross-Site_Request_Forgery_Prevention_Cheat_Sheet.html)
        http.csrf(csrf -> csrf.disable());

        // svim korisnicima dopusti da pristupe sledecim putanjama:
        // komunikacija izmedju klijenta i servera je stateless posto je u pitanju REST aplikacija
        // ovo znaci da server ne pamti nikakvo stanje, tokeni se ne cuvaju na serveru
        // ovo nije slucaj kao sa sesijama koje se cuvaju na serverskoj strani - STATEFULL aplikacija
        http.sessionManagement(session -> {
            session.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        });

        //sve neautentifikovane zahteve obradi uniformno i posalji 401 gresku
        http.exceptionHandling(exceptionHandling-> {
            exceptionHandling.authenticationEntryPoint(restAuthenticationEntryPoint);
        });


        http.authorizeHttpRequests(requests -> {
            requests .requestMatchers("/api/users/**").permitAll()
//                    .requestMatchers("/api/accommodations/").permitAll()
                    .requestMatchers("/api/amenities/").permitAll()
                    .requestMatchers("/api/email/**").permitAll()
                    .requestMatchers("/socket/**").permitAll()
                    .requestMatchers("/h2-console/").permitAll()
//                    .requestMatchers("/api/accommodations").permitAll()
                    .requestMatchers(new AntPathRequestMatcher("/error")).permitAll()
                    .requestMatchers(new AntPathRequestMatcher("/h2-console/**")).permitAll()


//                    .requestMatchers("/api/requests/**").permitAll()
//                    .requestMatchers("api/comments/**").permitAll()
                    // ukoliko ne zelimo da koristimo @PreAuthorize anotacije nad metodama kontrolera, moze se iskoristiti hasRole() metoda da se ogranici
                    // koji tip korisnika moze da pristupi odgovarajucoj ruti. Npr. ukoliko zelimo da definisemo da ruti 'admin' moze da pristupi
                    // samo korisnik koji ima rolu 'ADMIN', navodimo na sledeci nacin:
                    //.requestMatchers("/api/whoami").hasRole("USER")
//                    .requestMatchers("/api/accommodations").hasAuthority("ROLE_GUEST")
//                    .requestMatchers("/api/accommodations").hasAuthority("ROLE_HOST")
                    // za svaki drugi zahtev korisnik mora biti autentifikovan

                    .anyRequest().authenticated();
        });



        // umetni custom filter TokenAuthenticationFilter kako bi se vrsila provera JWT tokena
        http.addFilterBefore(new TokenAuthenticationFilter(tokenUtils,  userDetailsService()), UsernamePasswordAuthenticationFilter.class);

        // ulancavanje autentifikacije
        http.authenticationProvider(authenticationProvider());

        return http.build();
    }

    // metoda u kojoj se definisu putanje za igorisanje autentifikacije
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        // Autentifikacija ce biti ignorisana ispod navedenih putanja (kako bismo ubrzali pristup resursima)
        // Zahtevi koji se mecuju za web.ignoring().antMatchers() nemaju pristup SecurityContext-u
        // Dozvoljena POST metoda na ruti /auth/login, za svaki drugi tip HTTP metode greska je 401 Unauthorized
        return (web) -> web.ignoring()
                .requestMatchers(HttpMethod.POST, "/api/users/login");



        // Ovim smo dozvolili pristup statickim resursima aplikacije
//                .requestMatchers(HttpMethod.GET, "/", "/webjars/", "/.html", "favicon.ico",
//                        "//.html", "//.css", "//.js");

    }
}