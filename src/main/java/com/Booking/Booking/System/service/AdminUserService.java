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

    //  CREATE 
    public AdminUser saveAdmin(AdminUser admin) {
        return adminUserRepository.save(admin);
    }

    //  READ 
    public List<AdminUser> getAllAdmins() {
        return adminUserRepository.findAll();
    }

    public Optional<AdminUser> getAdminById(Long id) {
        return adminUserRepository.findById(id);
    }

    //  UPDATE 
    public AdminUser updateAdmin(Long id, AdminUser adminDetails) {
        Optional<AdminUser> optionalAdmin = adminUserRepository.findById(id);
        if (optionalAdmin.isPresent()) {
            AdminUser existingAdmin = optionalAdmin.get();
            existingAdmin.setName(adminDetails.getName());
            existingAdmin.setEmail(adminDetails.getEmail());
            existingAdmin.setPhone(adminDetails.getPhone());
            existingAdmin.setAdminLevel(adminDetails.getAdminLevel());
            return adminUserRepository.save(existingAdmin);
        }
        return null;
    }

    //  DELETE 
    public void deleteAdmin(Long id) {
        adminUserRepository.deleteById(id);
    }
}
