FROM --platform=linux/arm64 node:18 AS build
WORKDIR /usr/src/app

COPY package.json package-lock.json angular.json tsconfig*.json ./

RUN npm install

COPY src ./src
COPY public ./public

RUN npm run build

FROM --platform=linux/arm64 nginx:stable-alpine
COPY --from=build /usr/src/app/dist/front /usr/share/nginx/html
EXPOSE 80