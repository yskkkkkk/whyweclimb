FROM node:alpine
ENV PORT 3000
WORKDIR /usr/src/app
COPY package*.json ./
RUN apk --no-cache add tzdata && \
        cp /usr/share/zoneinfo/Asia/Seoul /etc/localtime && \
        echo "Asia/Seoul" > /etc/timezone
RUN npm i --force -y
COPY ./ ./
ENV NODE_ENV production
RUN npm run build
CMD [ "npm", "run", "start"]

