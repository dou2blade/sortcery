import 'package:flutter/material.dart';

class AuthSubmitButton extends StatelessWidget {
  final String label;
  final VoidCallback onPressed;
  final IconData icon;
  final bool loading;

  const AuthSubmitButton({ 
    super.key,
    required this.label,
    required this.onPressed,
    required this.icon,
    required this.loading
  });

  @override
  Widget build(BuildContext context) {
    return Padding(
      padding: EdgeInsets.all(6),
      child: SizedBox(
        width: double.infinity,
        height: 48,
        child: ElevatedButton(
          onPressed: onPressed, 
          style: ElevatedButton.styleFrom( 
            foregroundColor: Colors.white,
            backgroundColor: Theme.of(context).colorScheme.primary
          ),
          child: loading
            ? const CircularProgressIndicator(strokeWidth: 2)
            : Text(label)
        )
      )
    );
  }
}
