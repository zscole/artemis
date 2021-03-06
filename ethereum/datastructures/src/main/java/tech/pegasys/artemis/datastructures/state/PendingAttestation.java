/*
 * Copyright 2019 ConsenSys AG.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */

package tech.pegasys.artemis.datastructures.state;

import com.google.common.primitives.UnsignedLong;
import java.util.Objects;
import net.consensys.cava.bytes.Bytes;
import net.consensys.cava.bytes.Bytes32;
import net.consensys.cava.ssz.SSZ;
import tech.pegasys.artemis.datastructures.operations.AttestationData;

public class PendingAttestation {

  private Bytes32 aggregation_bitfield;
  private AttestationData data;
  private Bytes32 custody_bitfield;
  private UnsignedLong slot_included;

  public PendingAttestation(
      Bytes32 aggregation_bitfield,
      AttestationData data,
      Bytes32 custody_bitfield,
      UnsignedLong slot_included) {
    this.aggregation_bitfield = aggregation_bitfield;
    this.data = data;
    this.custody_bitfield = custody_bitfield;
    this.slot_included = slot_included;
  }

  public static PendingAttestation fromBytes(Bytes bytes) {
    return SSZ.decode(
        bytes,
        reader ->
            new PendingAttestation(
                Bytes32.wrap(reader.readBytes()),
                AttestationData.fromBytes(reader.readBytes()),
                Bytes32.wrap(reader.readBytes()),
                UnsignedLong.fromLongBits(reader.readUInt64())));
  }

  public Bytes toBytes() {
    return SSZ.encode(
        writer -> {
          writer.writeBytes(aggregation_bitfield);
          writer.writeBytes(data.toBytes());
          writer.writeBytes(custody_bitfield);
          writer.writeUInt64(slot_included.longValue());
        });
  }

  @Override
  public int hashCode() {
    return Objects.hash(aggregation_bitfield, data, custody_bitfield, slot_included);
  }

  @Override
  public boolean equals(Object obj) {
    if (Objects.isNull(obj)) {
      return false;
    }

    if (this == obj) {
      return true;
    }

    if (!(obj instanceof PendingAttestation)) {
      return false;
    }

    PendingAttestation other = (PendingAttestation) obj;
    return Objects.equals(this.getAggregation_bitfield(), other.getAggregation_bitfield())
        && Objects.equals(this.getData(), other.getData())
        && Objects.equals(this.getCustody_bitfield(), other.getCustody_bitfield())
        && Objects.equals(this.getSlot_included(), other.getSlot_included());
  }

  /** ******************* * GETTERS & SETTERS * * ******************* */
  public Bytes32 getAggregation_bitfield() {
    return aggregation_bitfield;
  }

  public void setAggregation_bitfield(Bytes32 aggregation_bitfield) {
    this.aggregation_bitfield = aggregation_bitfield;
  }

  public AttestationData getData() {
    return data;
  }

  public void setData(AttestationData data) {
    this.data = data;
  }

  public Bytes32 getCustody_bitfield() {
    return custody_bitfield;
  }

  public void setCustody_bitfield(Bytes32 custody_bitfield) {
    this.custody_bitfield = custody_bitfield;
  }

  public UnsignedLong getSlot_included() {
    return slot_included;
  }

  public void setSlot_included(UnsignedLong slot_included) {
    this.slot_included = slot_included;
  }
}
