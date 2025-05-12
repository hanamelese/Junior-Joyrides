import { NestFactory } from '@nestjs/core';
import { AppModule } from './app.module';
import { hostname } from 'os';

async function bootstrap() {
  const app = await NestFactory.create(AppModule);
  // app.setGlobalPrefix('api', 
  //   { exclude: ['/shop', '/admin-dashboard', '/volunteer-dashboard'], });
  // app.enableCors({
  //   origin: ['http://127.0.0.1:8080', 'http://another-allowed-origin.com'],
  //   methods: 'GET,HEAD,PUT,PATCH,POST,DELETE',
  //   allowedHeaders: 'Content-Type, Authorization',
  //   credentials: true,
  // });
  // app.enableCors({
  //   origin: 'http://localhost:3000', // Allow requests from your React app
  //   methods: 'GET,HEAD,POST,PUT,PATCH,DELETE', // Allowed HTTP methods
  //   credentials: true,
  // })


  await app.listen(process.env.PORT ?? 3000);
}
bootstrap();
