package com.rawlabs.das.sdk.java;

import com.rawlabs.protocol.das.Row;

import java.util.Iterator;
import java.io.Closeable;

public interface DASExecuteResult extends Iterator<Row>, Closeable {}
