import random
import math

#generacion de clave
def generacionClave():
    #1 seleccionar numeros primos
    p = 5 
    q = 7

    #2 calcule p*q
    n = p*q

    #3 calcule 	Φ(n)
    fi = (p-1)*(q-1)

    #4 seleccione e
    e = random.randint(1,n)
    print(e)

    #5 calcule clave privada 
    d = 0
    dd = 2
    while(math.pow(n, dd-1)%p != 1):
        dd += 1
    d = dd
    print(d)

def fermat(a):
    d = a + 1
    
    print(d)
    return d

def MCD(e, fi):
    print(e*fi)

#MCD(2,3)
generacionClave()


