#!/usr/bin/env python3
import csv

def load_lazy_file(file,file_type='csv'):
  file_type = file.split('.')[-1]

  if file_type == 'csv':
    with open(file,'r') as f:
      header = f.readline().replace('\n','').split(',')
      for line in csv.DictReader(f, fieldnames=header):
        yield line
  elif file_type == 'gz':
    import gzip
    with gzip.open(file,'rt') as f:
      header = f.readline().replace('\n','').split(',')
      for line in csv.DictReader(f, fieldnames=header):
        yield line
  elif file_type == 'json':
    import json
    with open(file,'r') as f:
      for line in f:
        yield json.loads(line)

def json_to_csv(file):
  import os

  if os.path.splitext(file)[1] not in ['.jsonl','.json']:
    raise Exception('Input file must be json or jsonl (json lines) format.')

  dirname           = os.path.dirname(file)
  file_no_extension = os.path.basename(file).split('.')[0]

  with open(os.path.join(dirname, file_no_extension + '.csv'),'w') as f:
    csv_file = csv.writer(f)
    for row in iter(load_lazy_file(file)):
      csv_file.writerow(row.values())

def main(file):
  json_to_csv(file)

if __name__ == "__main__":
  import argparse, sys, pathlib

  csv.field_size_limit(sys.maxsize)

  parser = argparse.ArgumentParser(description='')
  parser.add_argument('-f','--file', help='Path to the file', required=True)
  args = parser.parse_args()

  main(args.file)
