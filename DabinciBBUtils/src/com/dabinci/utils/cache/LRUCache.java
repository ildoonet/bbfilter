package com.dabinci.utils.cache;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

/*
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
/**
 * This class implements a Generic LRU Cache
 * 
 * 
 * @author Ignacio J. Ortega
 * 
 */

public class LRUCache {

	protected class CacheNode {

		private CacheNode prev;
		private CacheNode next;
		public Object value;
		public Object key;

		private CacheNode() {
		}
	}

	private int cacheSize;
	protected Hashtable nodes;
	protected CacheNode first;
	protected CacheNode last;
	
	public LRUCache(int i) {
		cacheSize = i;
		nodes = new Hashtable(i);
	}

	public int size() {
		return nodes.size();
	}

	public boolean containsKey(Object key) {
		return nodes.containsKey(key);
	}

	public synchronized Object get(Object key) {
		CacheNode node = (CacheNode) nodes.get(key);
		if (node == null) {
			return null;
		}
		moveToHead(node);
		return node.value;
	}

	public synchronized void put(Object key, Object value) {
		CacheNode node = (CacheNode) nodes.get(key);
		if (node == null) {
			if (nodes.size() >= cacheSize) {
				if (last != null)
					nodes.remove(last.key);
				removeLast();
			}

			node = new CacheNode();
			nodes.put(key, node);
		}
		node.value = value;
		node.key = key;
		moveToHead(node);
	}

	public synchronized Object remove(Object key) {
		CacheNode node = (CacheNode) nodes.remove(key);
		if (node == null) {
			return null;
		}

		if (node.prev != null) {
			node.prev.next = node.next;
		}

		if (node.next != null) {
			node.next.prev = node.prev;
		}

		if (last == node) {
			last = node.prev;
		}

		if (first == node) {
			first = node.next;
		}

		return node.value;
	}

	public synchronized void clear() {
		nodes.clear();
		
		first = null;
		last = null;
	}

	protected void removeLast() {
		if (last != null) {
			if (last.prev != null)
				last.prev.next = null;
			else
				first = null;
			last = last.prev;
		}
	}
	
	protected CacheNode getLast() {
		return last;
	}

	private void moveToHead(CacheNode node) {
		if (node == first)
			return;
		if (node.prev != null)
			node.prev.next = node.next;
		if (node.next != null)
			node.next.prev = node.prev;
		if (last == node)
			last = node.prev;
		if (first != null) {
			node.next = first;
			first.prev = node;
		}
		first = node;
		node.prev = null;
		if (last == null)
			last = first;
	}

	public synchronized Vector getList(){
		Vector list = new Vector();
		for(Enumeration e = nodes.elements(); e.hasMoreElements();){
			CacheNode node = (CacheNode) e.nextElement();
			if (node == null) {
				continue;
			}
			list.addElement(node.value);
		}
		return list;
	}
}