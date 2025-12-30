FROM oven/bun:1.3.5 as build
WORKDIR /app
COPY . .
RUN bun install
RUN bun run build
FROM nginx:1.28.1 AS exec
LABEL authors="Pinta"
WORKDIR /app
COPY nginx/nginx.conf /etc/nginx/nginx.conf
COPY nginx/cert.pem /etc/nginx/cert.pem
COPY nginx/key.pem /etc/nginx/key.pem
COPY --from=build /app/dist/homeweb/browser /usr/share/nginx/html
EXPOSE 80
EXPOSE 443
ENV TZ=Europe/Rome

