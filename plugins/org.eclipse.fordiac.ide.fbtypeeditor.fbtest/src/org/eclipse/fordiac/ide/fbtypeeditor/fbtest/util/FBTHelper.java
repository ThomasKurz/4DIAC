/*******************************************************************************
 * Copyright (c) 2011, 2012 TU Wien ACIN
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Ingo Hegny, ALois Zoitl
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.fbtest.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.OutputPrimitive;
import org.eclipse.fordiac.ide.model.libraryElement.ServiceSequence;
import org.eclipse.fordiac.ide.model.libraryElement.ServiceTransaction;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;

public class FBTHelper {

	static public int getEISize(FBType fbType) {
		int retval=0;
		try {
			retval = fbType.getInterfaceList().getEventInputs().size();
		} catch (Exception e) {
			retval=0;
		}
		return retval;
	}
	static public int getEOSize(FBType fbType) {
		int retval=0;
		try {
			retval = fbType.getInterfaceList().getEventOutputs().size();
		} catch (Exception e) {
			retval=0;
		}
		return retval;
	}

	static public int getDISize(FBType fbType) {
		int retval=0;
		try {
			retval = fbType.getInterfaceList().getInputVars().size();
		} catch (Exception e) {
			retval=0;
		}
		return retval;
	}
	
	static public int getDOSize(FBType fbType) {
		int retval=0;
		try {
			retval = fbType.getInterfaceList().getOutputVars().size();
		} catch (Exception e) {
			retval=0;
		}
		return retval;
	}
	
	static public int getEIID(FBType fbType, String eventName) {
		int retval = -1;

		try {
		for (Iterator<Event> iterator = fbType.getInterfaceList().getEventInputs().iterator(); iterator
				.hasNext();) {
			Event entry = iterator.next();
			if (entry.getName().equals(eventName)) {
				retval = fbType.getInterfaceList().getEventInputs().indexOf(entry);
				break;
			}
		}
		} catch (Exception e) {
			retval = -2;
		}
		return retval;
	}

	static public int getEOID(FBType fbType, String eventName) {
		int retval = -1;
		
		try {
			for (Iterator<Event> iterator = fbType.getInterfaceList().getEventOutputs().iterator(); iterator
					.hasNext();) {
				Event entry = iterator.next();
				if (entry.getName().equals(eventName)) {
					retval = fbType.getInterfaceList().getEventOutputs().indexOf(entry);
					break;
				}
			}
		} catch (Exception e) {
			retval = -2;
		}
		return retval;
	}

	static public int getDIID(final FBType fbType, final String DIName) {
		int retval = -1;
		try {
		for (Iterator<VarDeclaration> iterator = fbType.getInterfaceList().getInputVars().iterator(); iterator
				.hasNext();) {
			VarDeclaration entry = iterator.next();
			if (entry.getName().equals(DIName)) {
				retval = fbType.getInterfaceList().getInputVars().indexOf(entry);
				break;
			}
		}
		}
		catch (Exception e) {
			retval = -2;
		}
		return retval;
	}
	
	static public String getDIDataType(final FBType fbType, final int DIID) {
		String retval = null;
		
		try {
		if ((DIID>=0) && (fbType.getInterfaceList().getInputVars().size()>=DIID)) {
			retval=fbType.getInterfaceList().getInputVars().get(DIID).getType().getName();
		}
		} catch (Exception e) {
			retval = null;
		}
		return retval;
	}
	
	static public int getDOID(final FBType fbType, final String DOName) {
		int retval = -1;
		try {
		for (Iterator<VarDeclaration> iterator = fbType.getInterfaceList().getOutputVars().iterator(); iterator
				.hasNext();) {
			VarDeclaration entry = iterator.next();
			if (entry.getName().equals(DOName)) {
				retval = fbType.getInterfaceList().getOutputVars().indexOf(entry);
				break;
			}
		}
		}
		catch (Exception e) {
			retval = -2;
		}
		return retval;
	}
	
	static public String getDODataType(final FBType fbType, final int DOID) {
		String retval = null;
		
		try {
		if ((DOID>=0) && (fbType.getInterfaceList().getOutputVars().size()>=DOID)) {
			retval=fbType.getInterfaceList().getOutputVars().get(DOID).getType().getName();
		}
		} catch (Exception e) {
			retval = null;
		}
		return retval;
	}

	static public String getDINameByIndex(final FBType fbType, final int index) {
		String retval = null;
		
		try {
			retval = fbType.getInterfaceList().getInputVars().get(index).getName();
			
		} catch (Exception e) {
			retval = null;
		}
		return retval;
	}

	static public String getDONameByIndex(final FBType fbType, final int index) {
		String retval = null;
		
		try {
			retval = fbType.getInterfaceList().getOutputVars().get(index).getName();
			
		} catch (Exception e) {
			retval = null;
		}
		return retval;
	}
	
	static public String getEINameByIndex(final FBType fbType, final int index) {
		String retval = null;
		
		try {
			retval = fbType.getInterfaceList().getEventInputs().get(index).getName();
			
		} catch (Exception e) {
			retval = null;
		}
		return retval;
	}
	
	static public String getEONameByIndex(final FBType fbType, final int index) {
		String retval = null;
		
		try {
			retval = fbType.getInterfaceList().getEventOutputs().get(index).getName();
			
		} catch (Exception e) {
			retval = null;
		}
		return retval;
	}
	
	static public List<TestSequence> extractTestSequences(FBType fbType) {
		
		List<TestSequence> testSequences = new ArrayList<TestSequence>();
		

		for (int i=0; i<fbType.getService().getServiceSequence().size(); i++) {
			ServiceSequence sq;
			sq = fbType.getService().getServiceSequence().get(i);
			
			testSequences.add(extractTestSequence(fbType,sq));

		}
		return testSequences;
	}
	
	static public TestSequence extractTestSequence(FBType fbType, ServiceSequence sq) {
		TestSequence ts = new TestSequence();
		ts.setName(sq.getName());
		ts.setRelatedModelElement(sq);
		int nNumberOfST = sq.getServiceTransaction().size();
		for (int j=0; j<nNumberOfST; j++) {
			TestTransaction tt = new TestTransaction();
			ts.addTestTransaction(tt);
			ServiceTransaction st = sq.getServiceTransaction().get(j);
			tt.setRelatedModelElement(st);
			if (null != st.getInputPrimitive()) {
				String event = st.getInputPrimitive().getEvent();
				Boolean inputQualifier=false;
				boolean useInputQualifier=false;
				/** Evaluate Event-Data */
				if (event.contains("-")) {
					event = event.substring(0, event.indexOf("-"));
					inputQualifier=false;
					useInputQualifier=true;
				} else if(event.contains("+")) {
					event = event.substring(0, event.indexOf("+"));
					inputQualifier=true;
					useInputQualifier=true;
				}

				int eventID = FBTHelper.getEIID(fbType, event);
				if (0<=eventID) {
					TestPrimitive tp = new TestPrimitive(new org.eclipse.fordiac.ide.fbtypeeditor.fbtest.util.Event(event, eventID, true));
					tp.setRelatedModelElement(st.getInputPrimitive());
					tt.setInputPrimitive(tp);

					if (useInputQualifier) {
						String qiDT = null;
						int qiID = FBTHelper.getDIID(fbType, "QI");

						if (0<=qiID) {
							qiDT = FBTHelper.getDIDataType(fbType, qiID);
							DataVariable qi = new DataVariable("QI", qiID, qiDT, true, inputQualifier.toString() );
							tp.addData(qi);
						}
					}


					/*** Parameter evaluation */
					//					String params = ;
					//TODO extract input data
					if (null != st.getInputPrimitive().getParameters()) {
						String[] parameters = st.getInputPrimitive().getParameters().split(";");
						for (String param : parameters) {
							param = param.replace(" ", "");
							param = param.replace("\t", "");
							String[] left= param.split(":=");
							if (left.length==2) {
								String diDT = null;
								int diID = FBTHelper.getDIID(fbType, left[0]);

								if (0<=diID) {
									diDT = FBTHelper.getDIDataType(fbType, diID);
									DataVariable di = new DataVariable(left[0], diID, diDT, true, left[1] );
									tp.addData(di);
								}
							}
						}
					}

				} 
			}
			if (null != st.getOutputPrimitive()) {
				if (st.getOutputPrimitive().size()>0) {
					for (Iterator<OutputPrimitive> iterator = st.getOutputPrimitive().iterator(); iterator.hasNext();) {
						OutputPrimitive op = iterator.next();
						TestPrimitive tp = new TestPrimitive(null);
						
						String event = op.getEvent();
						Boolean outputQualifier=false;
						boolean useOutputQualifier=false;
						/** Evaluate Event-Data */
						if (event.contains("-")) {
							event = event.substring(0, event.indexOf("-"));
							outputQualifier=false;
							useOutputQualifier=true;
						} else if(event.contains("+")) {
							event = event.substring(0, event.indexOf("+"));
							outputQualifier=true;
							useOutputQualifier=true;
						}
						
						int eventID = FBTHelper.getEOID(fbType, event);
						if (0<=eventID) {
							tp.setEvent(new org.eclipse.fordiac.ide.fbtypeeditor.fbtest.util.Event(event, eventID, false));
							tp.setRelatedModelElement(op);
							tt.addOutputPrimitives(tp);

							if (useOutputQualifier) {
								String qoDT = null;
								int qoID = FBTHelper.getDOID(fbType, "QO");

								if (0<=qoID) {
									qoDT = FBTHelper.getDODataType(fbType, qoID);
									DataVariable qo = new DataVariable("QO", qoID, qoDT, false, outputQualifier.toString() );
									tp.addData(qo);
								}
							}
						}
						
						/*** Parameter evaluation */
						//					String params = ;
						//TODO extract output data
						if (null != op.getParameters()) {
							String[] parameters = op.getParameters().split(";");
							for (String param : parameters) {
								param = param.replace(" ", "");
								param = param.replace("\t", "");
								String[] left= param.split(":=");
								if (left.length==2) {
									String doDT = null;
									int doID = FBTHelper.getDOID(fbType, left[0]);

									if (0<=doID) {
										doDT = FBTHelper.getDODataType(fbType, doID);
										DataVariable dout = new DataVariable(left[0], doID, doDT, false, left[1] );
										tp.addData(dout);
									}
								}
							}
						}

						
						
					}
					
					
				}
			}
		}
		return ts;
	}
	
	public static int getMaxOutputPrimitives(List<TestSequence> paTests) {
		int retval = 0;
		
		for (Iterator<TestSequence> TSiterator = paTests.iterator(); TSiterator.hasNext();) {
			TestSequence TS = TSiterator.next();
			if ((TS.getTestTransactions() != null) && (TS.getTestTransactions().size()>0)) {
				for (Iterator<TestTransaction> TTiterator = TS.getTestTransactions().iterator(); TTiterator.hasNext();) {
					TestTransaction TT = TTiterator.next();
					if (null != TT.getOutputPrimitives()) {
						retval = Math.max(retval, TT.getOutputPrimitives().size());
					}
				}
			}
			
		}
		
		return retval;
		
	}
	
}
