import random
import copy
import time

def randomSubSet(n,x):
	if(x>n):
		x = n
	return set(random.sample(range(1,n+1),x))
	
def randomSubSetFamily(m,n,d):
	result = []
	X = set()
	for i in range(n):
		X.add(i+1)
	for i in range(m):
		k = random.randint(1,d+1)
		
		tmp = randomSubSet(n,k)
		if len(tmp)==0:
			print("Error++++++++++++++++++++++++")
		X = X - tmp
		result.append(tmp)
	for x in X:
		k = random.randint(0,m)
		tmp = result[k]
		tmp.add(x)
	return result

def greedyCoverSet(X,F):
	result = []
	left = len(X)
	T = copy.deepcopy(F)
	while left>0:
		k = 0
		for i in range(len(T)):
			if len(T[i])>len(T[k]):
				k = i
		if len(T[k])==0:
			return []
		left -= len(T[k])
		result.append(k)
		tmp = T[k]
		for i in range(len(T)):
			if i!=k:
				T[i] = T[i] - tmp
		tmp.clear()
	return result

def Test(m,n,d):
	X = [i+1 for i in range(n)]
	F = randomSubSetFamily(m,n,d)
	"""
	print(X)
	for i in range(len(F)):
		print(i,":",F[i])
	
	print()
	"""
	start = time.time()
	res = greedyCoverSet(X,F)
	end = time.time()
	test = set()
	"""
	for x in res:
		test = test|F[x]
	print(test)
	"""
	print(len(res))
	print(end-start)

for i in range(10):
	Test(5000,5000,20)
			