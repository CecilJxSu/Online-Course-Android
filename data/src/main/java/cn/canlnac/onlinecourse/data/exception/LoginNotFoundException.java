/**
 * Copyright (C) 2015 Fernando Cejas Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cn.canlnac.onlinecourse.data.exception;

/**
 * Exception throw by the application when a Login search can't return a valid result.
 */
public class LoginNotFoundException extends Exception {

  public LoginNotFoundException() {
    super();
  }

  public LoginNotFoundException(final String message) {
    super(message);
  }

  public LoginNotFoundException(final String message, final Throwable cause) {
    super(message, cause);
  }

  public LoginNotFoundException(final Throwable cause) {
    super(cause);
  }
}
