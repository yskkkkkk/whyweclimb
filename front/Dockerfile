FROM node
WORKDIR /usr/src/app
COPY package*.json ./
RUN npm i --force -y
COPY . .
EXPOSE 3000
RUN npm run build
CMD [ "npm", "run", "start"]