import { err, errAsync, ok, Result } from "neverthrow";
import { HttpError } from "./http-errors";

type RequestOptions = {
  headers?: HeadersInit;
  body?: object | FormData;
};

type Method = "GET" | "POST" | "PUT" | "DELETE";

export class HttpClient {
  private baseUrl: string;

  constructor(baseUrl: string) {
    this.baseUrl = baseUrl;
  }

  private async request<T>(
    method: Method,
    url: string,
    options: RequestOptions = {}
  ): Promise<Result<T, HttpError>> {
    const URL = `${this.baseUrl}${url}`;
    try {
      const { headers, body } = options;
      const isFormData = body instanceof FormData;

      const response = await fetch(URL, {
        method,
        headers: isFormData
          ? headers
          : {
              "Content-Type": "application/json",
              ...headers,
            },
        body: isFormData ? body : body ? JSON.stringify(body) : null,
      });

      if (!response.ok) {
        const text = await response.text();
        const message =
          "Error - " + text + " - " + URL + " - " + response.status;
        return errAsync(new HttpError(message, response.status));
      }

      const data: T = await response.json();
      return ok(data);
      /* eslint-disable  @typescript-eslint/no-explicit-any */
    } catch (error: any) {
      const message = error.message + " - " + URL + " - " + 500;
      return err(new HttpError(message, 500));
    }
  }

  get<T>(url: string, options?: RequestOptions) {
    return this.request<T>("GET", url, options);
  }

  post<T>(url: string, body: object | FormData, options?: RequestOptions) {
    return this.request<T>("POST", url, { ...options, body });
  }

  put<T>(url: string, body: object, options?: RequestOptions) {
    return this.request<T>("PUT", url, { ...options, body });
  }

  delete<T>(url: string, options?: RequestOptions) {
    return this.request<T>("DELETE", url, options);
  }

  withPrefix(path: string) {
    this.baseUrl = this.baseUrl;

    return new HttpClient(this.baseUrl + path);
  }
}

export const httpClient = new HttpClient("http://localhost:8080");
