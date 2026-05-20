import 'package:flutter/material.dart';

class AuthHeader extends StatelessWidget {
  final String title;
  final String subtitle;

  const AuthHeader({
    super.key,
    required this.title,
    required this.subtitle
  });

  @override
  Widget build(BuildContext context) {
    return Column( 
      crossAxisAlignment: CrossAxisAlignment.center,
      children: [
        Text( 
          title,
          style: Theme.of(context).textTheme.titleLarge
        ),
        const SizedBox(height: 4),
        Text( 
          subtitle,
          style: Theme.of(context).textTheme.bodyMedium
        ),
        const SizedBox(height: 12)
      ]
    );
  }
}
