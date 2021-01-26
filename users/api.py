from flask import Flask
from flask_restful import Resource, Api
from flask_restful import reqparse
from flask.ext.mysql import MySQL

mysql = MySQL()
app = Flask(__name__)

# MySQL configurations
app.config['MYSQL_DATABASE_USER'] = 'root'
app.config['MYSQL_DATABASE_PASSWORD'] = 'rootroot'
app.config['MYSQL_DATABASE_DB'] = 'usersdb'
app.config['MYSQL_DATABASE_HOST'] = 'localhost'


mysql.init_app(app)

api = Api(app)

class AuthenticateUser(Resource):
    def post(self):
        try:
            # Parse the arguments

            parser = reqparse.RequestParser()
            parser.add_argument('email', type=str, help='Email address for Authentication')
            parser.add_argument('password', type=str, help='Password for Authentication')
            args = parser.parse_args()

            _userEmail = args['email']
            _userPassword = args['password']

            conn = mysql.connect()
            cursor = conn.cursor()
            cursor.callproc('sp_AuthenticateUser',(_userEmail,))
            data = cursor.fetchall()

            
            if(len(data)>0):
                if(str(data[0][2])==_userPassword):
                    return {'status':200,'UserId':str(data[0][0])}
                else:
                    return {'status':100,'message':'Authentication failure'}

        except Exception as e:
            return {'error': str(e)}
                

class CreateUser(Resource):
    def post(self):
        try:
            # Parse the arguments
            parser = reqparse.RequestParser()
            parser.add_argument('email', type=str, help='Email address to create user')
            parser.add_argument('password', type=str, help='Password to create user')
            args = parser.parse_args()

            _userEmail = args['email']
            _userPassword = args['password']

            conn = mysql.connect()
            cursor = conn.cursor()
            cursor.callproc('spCreateUser',(_userEmail,_userPassword))
            data = cursor.fetchall()

            if len(data) is 0:
                conn.commit()
                return {'StatusCode':'200','Message': 'User creation success'}
            else:
                return {'StatusCode':'1000','Message': str(data[0])}

        except Exception as e:
            return {'error': str(e)}



api.add_resource(CreateUser, '/CreateUser')
api.add_resource(AuthenticateUser, '/AuthenticateUser')


if __name__ == '__main__':
    app.run(debug=True)