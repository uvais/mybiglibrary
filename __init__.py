from flask import *
from searcher import *
import mysql.connector
from mysqlConnecter import *
import os
import pdf
from searcher import *
from werkzeug import secure_filename



connectMysql = mysqlConnect()
lemon = Searcher()
app = Flask(__name__)
app.secret_key = 'F12Zr47j\3yX R~X@H!jmM]Lwf/,?KT'


@app.route('/')
def home():
	return render_template('index.html')

@app.route('/admin')
def adminhome():
	return render_template('adminlogin.html')
	

@app.route('/home', methods=['POST','GET'])
def login():
	if request.method == 'POST':
		u = request.form['uid']
		p = request.form['pwd']
		row = connectMysql.getMysqlQuerry(u);
		if row:
			dbuname = row[0];
			dbpass = row[1];
			if dbpass != p:
				return render_template('adminlogin.html', errormsg="Password mismatch")
			else:
				session['name'] = u
				return render_template('adminhome.html')
		else:
			return render_template('adminlogin.html', errormsg="User not found")
	elif request.method == "GET":
		return render_template('adminhome.html')
		
@app.route('/redirect')	
def rediret():
	return render_template('adminhome.html')

@app.route('/nologin')
def nologin():
	return render_template('adminlogin.html')

@app.route('/logout')	
def logout():
	session.clear()
	return render_template('adminlogin.html')

@app.route('/query', methods=['GET', 'POST'])
def query():
    if request.method == 'POST':
        q = request.form['searchbar'].lower()
        result = lemon.query(q)
	return render_template('result.html', result=result)

def allowed_file(filename):
	ALLOWED_EXTENSIONS = set(['pdf'])
	return '.' in filename and filename.rsplit('.', 1)[1] in ALLOWED_EXTENSIONS

@app.route('/admin/upload', methods=['GET', 'POST'])
def upload():

	UPLOAD_FOLDER = '/var/www/html/mybiglibrary/pdfs'
	app.config['UPLOAD_FOLDER'] = UPLOAD_FOLDER
	if request.method == 'POST':
		file = request.files.getlist('file[]')
		for uf in file:
			filename = uf.filename
			uf.save(os.path.join(app.config['UPLOAD_FOLDER'], uf.filename))
			pdf.convert_one(uf.filename, "/var/www/html/mybiglibrary/pdfs/", "/var/www/html/mybiglibrary/files/")
		os.system("/usr/local/hadoop/bin/hdfs dfs -copyFromLocal /var/www/html/mybiglibrary/files/* /user/biglibrary/files")
		os.system("rm -f /var/www/html/mybiglibrary/files/*")
	return render_template("adminhome.html", message="Successfully Uploaded to HDFS")

@app.route('/admin/index', methods=['GET', 'POST'])
def index():
    if request.method == 'POST':
        os.system("/usr/local/hadoop/bin/hadoop jar /var/www/html/mybiglibrary/TextIndexer/target/textIndexer-1.0.jar /user/biglibrary/files")
        return render_template("adminhome.html", message="Successfully loaded to MongoDb")

@app.route('/pdfs/<path:path>', methods=['GET', 'POST'])
def data(path):
    if request.method == 'GET':
        return send_from_directory('pdfs', path)

if __name__ == '__main__':
    app.debug = True
    app.run()