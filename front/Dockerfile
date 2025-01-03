FROM node:18

RUN npm install -g http-server

WORKDIR /src/app

COPY package*.json ./

COPY . .

EXPOSE 5173

RUN chmod +x ./entrypoint.sh

ENTRYPOINT [ "./entrypoint.sh" ]