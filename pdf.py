from pdfminer.pdfinterp import PDFResourceManager, PDFPageInterpreter
from pdfminer.converter import TextConverter
from pdfminer.layout import LAParams
from pdfminer.pdfpage import PDFPage
from cStringIO import StringIO
import os

def convert_pdf_to_txt(path):
    rsrcmgr = PDFResourceManager()
    retstr = StringIO()
    codec = 'utf-8'
    laparams = LAParams()
    device = TextConverter(rsrcmgr, retstr, codec=codec, laparams=laparams)
    fp = file(path, 'rb')
    interpreter = PDFPageInterpreter(rsrcmgr, device)
    password = ""
    maxpages = 0
    caching = True
    pagenos=set()

    for page in PDFPage.get_pages(fp, pagenos, maxpages=maxpages, password=password,caching=caching, check_extractable=True):
        interpreter.process_page(page)

    text = retstr.getvalue()

    fp.close()
    device.close()
    retstr.close()
    return text

def convert_one(filename, source, destination):
    fout = open(destination+filename.replace(".pdf", ".txt"), "w")
    fout.write(convert_pdf_to_txt(source+filename))


def convert_all(source, destination):
    for fname in os.listdir(source):
        fout = open(destination+fname.replace(".pdf", ".txt"), "w")
        fout.write(convert_pdf_to_txt(source+fname))
        print fname


if __name__ == "__main__":
    convert_all("/home/qbuser/pdfs/", "/home/qbuser/files/")