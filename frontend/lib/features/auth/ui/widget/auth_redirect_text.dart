import 'package:flutter/gestures.dart';
import 'package:flutter/material.dart';

class AuthRedirectText extends StatelessWidget {
  final String label;
  final String actionText;
  final VoidCallback onTap;

  const AuthRedirectText({
    super.key,
    required this.label,
    required this.actionText,
    required this.onTap,
  });

  @override
  Widget build(BuildContext context) {
    return Align(
      alignment: Alignment.bottomRight,
      child: Padding(
        padding: EdgeInsetsGeometry.only(right: 12),
        child: RichText(
          text: TextSpan(
            style: Theme.of(context).textTheme.bodyMedium,
            children: [
            TextSpan(text: label),
            TextSpan(
              text: actionText,
              style: TextStyle(
                color: Theme.of(context).colorScheme.primary,
                fontWeight: FontWeight.w600,
                ),
              recognizer: TapGestureRecognizer()..onTap = onTap,
              )
            ]
            )
          )
        )
    );
  }
}
