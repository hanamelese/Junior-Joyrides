import { Controller, Get, Res } from '@nestjs/common';
import { Response } from 'express';
import { join } from 'path';

@Controller()
export class AppController {
  @Get('/')
  getHome() {
    return { message: 'Welcome to the API' };
  }
}



