package com.Booking.Booking.System.service;

import com.Booking.Booking.System.model.AdminUser;
import com.Booking.Booking.System.repository.AdminUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdminUserService {

    @Autowired
    private AdminUserRepository adminUserRepository;

    // ─── CREATE ──────────────────────────────────────────────────────────────────
    public AdminUser saveAdmin(AdminUser admin) {
        return adminUserRepository.save(admin);
    }

}
