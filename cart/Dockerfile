FROM node:15-alpine

# Create app directory
WORKDIR /usr/src/app

# Install app dependencies
# A wildcard is used to ensure both package.json AND package-lock.json are copied
# where available (npm@5+)
COPY package*.json ./
ENV DB_HOST="localhost"
ENV DB_PORT=3306
ENV DB_SCHEMA="cartsdb"
ENV DB_USER="mycartuser"
ENV DB_PWD="rootroot"

RUN npm install
# If you are building your code for production
# RUN npm ci --only=production

# Bundle app source
COPY . .

EXPOSE 3000
CMD [ "node", "index.js" ]