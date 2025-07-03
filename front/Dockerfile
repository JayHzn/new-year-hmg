FROM --platform=linux/arm64 node:18 AS build
WORKDIR /src

RUN npm install -g @angular/cli

COPY package.json package-lock.json angular.json tsconfig*.json ./

RUN npm ci

COPY . ./

RUN npm run build:prod

FROM --platform=linux/arm64 nginx:stable-alpine

RUN rm -rf /usr/share/nginx/html/*

COPY nginx.conf /etc/nginx/conf.d/default.conf

COPY --from=build /src/dist/front/browser/ /usr/share/nginx/html/

EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]