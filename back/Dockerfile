FROM node:18

WORKDIR /src/app

COPY package*.json ./

COPY . .

EXPOSE 3000

RUN chmod +x ./entrypoint.sh

ENTRYPOINT ["./entrypoint.sh"]