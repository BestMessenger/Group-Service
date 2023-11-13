    package com.messenger.groupservice.util;

    import com.messenger.groupservice.openfeign_client.UserProfileServiceClient;
    import feign.FeignException;
    import lombok.AllArgsConstructor;
    import org.springframework.stereotype.Component;

    @AllArgsConstructor
    @Component
    public class UserChecker {

        private final UserProfileServiceClient userProfileServiceClient;

        public boolean isExistUserInProfileService(Long userId) {
            try {
                userProfileServiceClient.getUserById(userId);
            } catch (FeignException.NotFound e) {
                return false;
            }
            return true;
        }
    }
