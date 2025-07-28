package com.banking.common.audit;

import com.banking.common.context.RequestContextHolder;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

@Component
public class AuditorAwareImpl implements AuditorAware<UUID> {


    @Override
    public Optional<UUID> getCurrentAuditor() {
        return Optional.ofNullable(RequestContextHolder.getContext().getUserId());
    }
}
