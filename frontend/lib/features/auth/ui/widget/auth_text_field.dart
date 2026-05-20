import 'package:flutter/material.dart';

class AuthTextField extends StatelessWidget {
  final TextEditingController controller;
  final String label;
  final IconData icon;
  final bool obscureText;
  final String? errorText;
  final bool hideErrorText;

  const AuthTextField({
    super.key,
    required this.controller,
    required this.label,
    required this.icon,
    this.obscureText = false,
    this.errorText,
    this.hideErrorText = false
  });

  @override
  Widget build(BuildContext context) {
    return Padding(
      padding: EdgeInsets.all(6),
      child: TextField( 
        controller: controller,
        obscureText: obscureText,
        decoration: InputDecoration(
          labelText: label,
          prefixIcon: Icon(icon),
          border: OutlineInputBorder(
            borderRadius: BorderRadius.circular(12)
          ),
          errorText: hideErrorText 
            ? errorText != null ? " " : null
            : errorText,
          errorStyle: hideErrorText ? TextStyle(height: 0) : null
        )
      )
    );
  }
}
