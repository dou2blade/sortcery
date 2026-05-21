import 'package:flutter/material.dart';
import 'package:sortcery/core/enums/user_role.dart';

class AuthStaffRolesDropdownMenu extends StatelessWidget {
  final UserRole value;
  final String? errorText;
  final ValueChanged<UserRole> onSelected;

  const AuthStaffRolesDropdownMenu({
    super.key,
    required this.value,
    this.errorText,
    required this.onSelected,
  });

  @override
  Widget build(BuildContext context) {
    return LayoutBuilder( 
      builder: (context, constraints) {
        return Padding(
          padding: EdgeInsets.all(6),
          child: DropdownMenu<UserRole>(
            dropdownMenuEntries: UserRole.values
              .where((role) => role != UserRole.consumer)
              .map((role) {
                return DropdownMenuEntry<UserRole>(value: role, label: role.name.toUpperCase());
              }).toList(),
            onSelected: (role) => role != null ? onSelected(role) : null,
            decorationBuilder: (context, child) => InputDecoration( 
              labelText: "Role",
              prefixIcon: Icon(Icons.verified_user),
              border: OutlineInputBorder(
                borderRadius: BorderRadius.circular(12)
              ),
              errorText: errorText,
            ),
            width: double.infinity,
            menuStyle: MenuStyle( 
              maximumSize: WidgetStatePropertyAll(Size.fromWidth(constraints.maxWidth))
            )
          )
        );
      }
    );
  }
}
