package notes.Bitset;

public class Bitset {
    private int[] bit;

    Bitset(int len){
        this.bit = new int[(int)Math.ceil((double)len / 32)];
    }

    public Bitset clone(){
        Bitset newBitset = new Bitset(32 * this.bit.length);
        System.arraycopy(this.bit, 0, newBitset.bit, 0, this.bit.length);
        return newBitset;
    }

    public String toString1(){
        StringBuffer sb = new StringBuffer();
        int idx = 0;
        for(int i = 0;i < bit.length;i++){
            int x = bit[i];
            for(int j = 31;j >= 0;j--){
                if(((x >> j) & 1) == 1){
                    sb.append("idx = " + (idx++) + " bit: " + 1 + '\n');
                }else{
                    sb.append("idx = " + (idx++) + " bit: " + 0 + '\n');
                }
            }
        }
        return sb.toString();
    }

    public String toString2(){
        StringBuffer sb = new StringBuffer();
        for(int i = 0;i < bit.length;i++){
            int x = bit[i];
            for(int j = 31;j >= 0;j--){
                if(((x >> j) & 1) == 1){
                    sb.append(1);
                }else{
                    sb.append(0);
                }
            }
        }
        return sb.toString();
    }

    // return x 的二进制表示
    public String getBit(int x){
        StringBuffer sb = new StringBuffer();
        for(int j = 31;j >= 0;j--){
            if(((x >> j) & 1) == 1){
                sb.append(1);
            }else{
                sb.append(0);
            }   
        }
        return sb.toString();
    }

    // bit << k
    public Bitset leftShift(int k){
        int shift = k / 32, offset = k % 32;    // shift: 需要左移多少个整个的int, offset: 还需要左移offset个bit
        if(offset == 0){
            for(int i = 0;i < bit.length - shift;i++){
                bit[i] = bit[i + shift];
            }
            clear(32 * (bit.length - shift));
        }else{
            for(int i = 0;i < bit.length - shift - 1;i++){
                bit[i] = bit[i + shift] << offset | ((bit[i + shift + 1] & (~((1 << (32 - offset)) - 1))) >> (32 - offset));
            }
            bit[bit.length - shift - 1] = bit[bit.length - 1] << offset;
            clear(32 * (bit.length - shift) - offset);
        }
        return this;
    }

    // bit >> k
    // 注意这里面的右移需要用逻辑右移(>>>), 而不能是算术右移(>>)
    public Bitset rightShift(int k){
        int shift = k / 32, offset = k % 32;
        if(offset == 0){
            for(int i = bit.length - 1;i >= shift;i--) bit[i] = bit[i - shift];
            rangeSet(0, shift * 32 - 1, 0);
        }else{
            for(int i = bit.length - 1;i >= shift + 1;i--){
                bit[i] = (bit[i - shift] >>> offset) | ((bit[i - shift - 1] & ((1 << offset) - 1)) << (32 - offset));
            }
            bit[shift] = bit[0] >>> offset;
            int endIdx = shift * 32 + offset - 1;
            rangeSet(0, endIdx, 0);
        }
        return this;
    }

    // 将下标 >= startBit 的这些bit全都清零
    public Bitset clear(int startBit){
        rangeSet(startBit, 32 * bit.length - 1, 0);
        return this;
    }

    // i: 下标为i的比特位
    public void set1(int i){
        int shift = i / 32, offset = i % 32;
        bit[shift] |= 1 << (32 - offset - 1);
    }

    // i: 下标为i的比特位
    public int get(int i){
        int shift = i / 32, offset = (i % 32);
        return (bit[shift] >> (32 - offset - 1)) & 1;
    }

    // this |= bitset, 要求bitset和this的bit长度要相等
    public Bitset unionFrom(Bitset bitset){
        for(int i = 0;i < this.bit.length;i++){
            this.bit[i] |= bitset.bit[i];
        }
        return this;
    }

    // l, r: bit位的下标, val: 0 or 1
    public Bitset rangeSet(int l, int r, int val){
        int shiftL = l / 32, offsetL = l % 32, shiftR = r / 32, offsetR = r % 32;
        if(val == 0){
            if(shiftL == shiftR){
                int leftMask = offsetL == 0 ? 0xffffffff : ((1 << (32 - offsetL)) - 1);     // 如果offsetL == 0, 1 << 32会溢出, 因此加一个判断
                int rightMask = (~((1 << (32 - offsetR - 1)) - 1));
                int mask = leftMask & rightMask;
                bit[shiftL] &= (~mask);
            }else{
                int leftMask = offsetL == 0 ? 0xffffffff : ((1 << (32 - offsetL)) - 1);     // 如果offsetL == 0, 1 << 32会溢出, 因此加一个判断
                int rightMask = (~((1 << (32 - offsetR - 1)) - 1));
                bit[shiftL] &= ~leftMask;
                bit[shiftR] &= ~rightMask;
                if(shiftL + 1 <= shiftR - 1){
                    for(int i = shiftL + 1;i <= shiftR - 1;i++){
                        bit[i] = 0;
                    }
                }
            }
        }else if(val == 1){
            if(shiftL == shiftR){
                int leftMask = offsetL == 0 ? 0xffffffff : ((1 << (32 - offsetL)) - 1);     // 如果offsetL == 0, 1 << 32会溢出, 因此加一个判断
                int rightMask = (~((1 << (32 - offsetR - 1)) - 1));
                int mask = leftMask & rightMask;
                bit[shiftL] |= mask;
            }else{
                int leftMask = offsetL == 0 ? 0xffffffff : ((1 << (32 - offsetL)) - 1);     // 如果offsetL == 0, 1 << 32会溢出, 因此加一个判断
                int rightMask = (~((1 << (32 - offsetR - 1)) - 1));
                bit[shiftL] |= leftMask;
                bit[shiftR] |= rightMask;
                if(shiftL + 1 <= shiftR - 1){
                    for(int i = shiftL + 1;i <= shiftR - 1;i++){
                        bit[i] = 0xffffffff;
                    }
                }
            }
        }
        return this;
    }

    // return the last index of 1
    public int lastIndex1(){
        for(int i = bit.length - 1;i >= 0;i--){
            int x = bit[i];
            for(int j = 0;j < 32;j++){
                if(((x >> j) & 1) == 1){
                    return 32 * i + 31 - j;
                }
            }
        }
        return -1;  // no ans
    }
}
