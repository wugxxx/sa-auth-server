package com.wuguoxing.saauthserver.repository.account;


import com.wuguoxing.saauthserver.entity.Account;
import org.springframework.data.jpa.domain.Specification;

public class AccountSpecification {

    public static Specification<Account> usernameLike(String username) {
        return (root, query, builder) -> builder.like(root.get("username"), "%" + username + "%");
    }

    public static Specification<Account> nicknameLike(String nickname) {
        return (root, query, builder) -> builder.like(root.get("nickname"), "%" + nickname + "%");
    }
}
