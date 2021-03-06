 package edu.umb.cs.threads.primes;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;
import java.util.ArrayList;

public class PrimeGenerator {
	protected long from, to;
	protected List<Long> primes;

	public PrimeGenerator(long from, long to){
		if(from >= 1 && to >= from){
			this.primes = new ArrayList<Long>();
			this.from = from;
			this.to = to;
		}else{
			throw new RuntimeException("Wrong input values: from=" + from + " to=" + to);
		}
	}
	
	public List<Long> getPrimes(){ return primes; };
	
	protected boolean isEven(long n){
		if(n%2 == 0){ return true; }
		else{ return false; }
	}
	
	protected boolean isPrime(long n){
		// 1 is not prime. 
		if(n == 1){ return false; }
		// Even numbers that are greater than 2 are not prime. 
		if( n > 2 && isEven(n) ){ return false; }
		long i;
		// Find a number "i" that can divide "n" 
        for (i = (long) Math.sqrt(n); n%i != 0 && i >= 1; i--){}
        // If such a number "i" is found, n is not prime. Otherwise, n is prime. 
        if (i == 1){ return true; }
        else{ return false; }
	}

	public void generatePrimes(){
		for (long n = from; n <= to; n++) {
			if( isPrime(n) ){ primes.add(n); }
        }
	}
	
	public static void main(String[] args) {
		// Single-threaded prime number generation (with generatePrimes())
		long startTime = System.currentTimeMillis();
		PrimeGenerator gen = new PrimeGenerator(1, 100);
		gen.generatePrimes();
		gen.getPrimes().forEach( (Long prime)-> System.out.print(prime + ", ") );
		System.out.println("\n" + gen.getPrimes().size() + " prime numbers are found.");
		long endTime = System.currentTimeMillis();
		System.out.println((endTime - startTime) + " ms");
		// Single-threaded prime number generation (without using generatePrimes())
		long startTime2 = System.currentTimeMillis();
		PrimeGenerator gen2 = new PrimeGenerator(1, 100);
		gen2.primes = LongStream.rangeClosed(gen.from, gen.to)
								.filter( (long n)->gen.isPrime(n) )
								.boxed()
								.collect(Collectors.toList());
		gen2.getPrimes().forEach( (Long prime)-> System.out.print(prime + ", ") );
		System.out.println("\n" + gen2.getPrimes().size() + " prime numbers are found.");
		long endTime2 = System.currentTimeMillis();
		System.out.println((endTime2 - startTime2) + " ms");
	}
}
