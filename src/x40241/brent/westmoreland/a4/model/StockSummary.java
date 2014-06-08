package x40241.brent.westmoreland.a4.model;

import android.os.Parcel;
import android.os.Parcelable;


/**
 * @author Jeffrey Peacock (Jeffrey.Peacock@uci.edu)
 */
public final class StockSummary implements Parcelable
{
	private long mId;
	private long   mSequence;
    private String  mName;
    private String  mSymbol;
    private Float  mPrice;
    private Float mMax;
    private Float mMin;
    private Float mAvg;
    private long mCount;
    private long mModified;
    
    public StockSummary(Parcel source) {    	
    	mId = source.readLong();
    	mSequence = source.readLong();
    	mName = source.readString();
    	mSymbol = source.readString();
    	mPrice = source.readFloat();
    	mMax = source.readFloat();
    	mMin = source.readFloat();
    	mAvg = source.readFloat();
    	mCount = source.readLong();
    	mModified = source.readLong();
	}

	public StockSummary() {
		//
	}
	
	public StockSummary(String symbol, Float price) {
		mSymbol = symbol;
		mPrice = price;
	}

	public long getId() {
		return mId;
	}
    
	public void setId(long stockID) {
		mId = stockID;
	}
	
    public long getSequence() {
        return mSequence;
    }
    public void setSequence (long sequence) {
        this.mSequence = sequence;
    }
    public String getName() {
        return mName;
    }
    public void setName (String name) {
        this.mName = name;
    }
    public String getSymbol() {
        return mSymbol;
    }
    public void setSymbol (String symbol) {
        this.mSymbol = symbol;
    }
    public Float getPrice() {
        return mPrice;
    }
    public void setPrice (Float price) {
        this.mPrice = price;
    }

	public Float getMax() {
		return mMax;
	}
	
	public void setMax(Float max) {
		mMax = max;
	}

	public Float getMin() {
		return mMin;
	}

	public void setMin(Float min) {
		mMin = min;
	}

	public Float getAvg() {
		return mAvg;
	}

	public void setAvg(Float avg) {
		mAvg = avg;
	}

	public long getCount() {
		return mCount;
	}

	public void setCount(long count) {
		mCount = count;
	}

	public long getModified() {
		return mModified;
	}

	public void setModified(long modified) {
		mModified = modified;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeLong(mId);
		dest.writeLong(mSequence);
		dest.writeString(mName);
		dest.writeString(mSymbol);
		dest.writeFloat(mPrice);
		dest.writeFloat(mMax);
		dest.writeFloat(mMin);
		dest.writeFloat(mAvg);
		dest.writeLong(mCount);
		dest.writeLong(mModified);
	}
	
	public static final Parcelable.Creator<StockSummary> CREATOR = new Parcelable.Creator<StockSummary>() {

		@Override
		public StockSummary createFromParcel(Parcel source) {
			return new StockSummary(source);
		}

		@Override
		public StockSummary[] newArray(int size) {
			return new StockSummary[size];
		}
		
	};
}


