enum UserRole {
  admin,
  retailer,
  manager,
  consumer,
}

class UserRoleMapper {
  static UserRole fromJson(String value) {
    switch (value) {
      case 'ADMIN':
        return UserRole.admin;
      case 'RETAILER':
        return UserRole.retailer;
      case 'MANAGER':
        return UserRole.manager;
      default:
        return UserRole.consumer;
    }
  }

  static String toJson(UserRole role) {
    switch (role) {
      case UserRole.admin:
        return 'ADMIN';
      case UserRole.retailer:
        return 'RETAILER';
      case UserRole.manager:
        return 'MANAGER';
      default:
        return 'CONSUMER';
    }
  }
}
