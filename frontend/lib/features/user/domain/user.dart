import "package:sortcery/core/enums/user_role.dart";

class User {
  final int id;
  final String firstName;
  final String? middleName;
  final String lastName;
  final String email;
  final UserRole role;

  const User({
    required this.id,
    required this.firstName,
    this.middleName,
    required this.lastName,
    required this.email,
    required this.role,
  });
}
