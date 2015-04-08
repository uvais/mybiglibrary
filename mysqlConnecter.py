import mysql.connector
from flask import *
ignore_words = ["what", "where", "how", "to", "from", "is", "was", "the", "this", "do", "does", "did", "will", "which", "why", "when"]

class mysqlConnect:
	def __init__(self):
		self.connection = mysql.connector.connect(host="localhost", database="testDb", password="root", user="root")
		self.cursor = self.connection.cursor() 
	def getMysqlQuerry(self, username):
		self.cursor.execute("select * from admindet where name='%s'" % username)
		row = self.cursor.fetchone()
		return row;

