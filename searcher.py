from pymongo import MongoClient
from stopwords import stopwords

class Searcher:

    def __init__(self):
        self.client = MongoClient('localhost', 27017)
        self.db = self.client.lemon

    def normalize(self, value, max):
        if max:
            return 1.0-(1.0/value)
        else:
            return 1.0/value

    def remove_stopwords(self, q):
        tokens = q.split()
        words = [x for x in tokens]
        for word in tokens:
            if word in stopwords:
                words.remove(word)
        return words


    def query(self, word):
        words = self.remove_stopwords(word)
        print words
        results = {}
        for word in words:
            rset = self.db.wordcount.find({"word": word});

            for r in rset:
                if r["file"] in results.keys():
                    results[r["file"]] += self.normalize(r["count"], True)
                else:
                    results[r["file"]] = self.normalize(r["count"], True)
        output = [[results[x], x] for x in results]
        return sorted(output, reverse=True)


if __name__ == "__main__":
    searcher = Searcher()
    value = searcher.query("who is the second daughter")
    print value
