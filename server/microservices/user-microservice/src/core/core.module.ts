import { Module } from '@nestjs/common';
import { APP_INTERCEPTOR, APP_PIPE } from '@nestjs/core';
import { LoggingInterceptor } from './logging.interceptor';
import { AllExceptionsFilter } from './all-exceptions.filter';
import { ValidationPipe } from './validation.pipe';

@Module({
  providers: [
    { provide: APP_INTERCEPTOR, useClass: AllExceptionsFilter },
    { provide: APP_INTERCEPTOR, useClass: LoggingInterceptor },
    { provide: APP_PIPE, useClass: ValidationPipe },
  ],
})
export class CoreModule {}
