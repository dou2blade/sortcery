import 'package:flutter/material.dart';
import 'package:sortcery/core/dependencies/dependency_injector.dart';
import 'package:sortcery/core/router/router.dart';

void main() {
  DependencyInjector.init();
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  @override
  Widget build(BuildContext context) {
    const primary = Colors.green;
    const secondary = Colors.lightGreen;

    return MaterialApp.router(
      routerConfig: router,
      theme: ThemeData( 
        useMaterial3: true,
        colorScheme: ColorScheme.fromSeed(
          seedColor: primary,
          primary: primary,
          secondary: secondary
        )
      )
    );
  }
}
