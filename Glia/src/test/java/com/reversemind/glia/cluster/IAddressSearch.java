package com.reversemind.glia.cluster;

import javax.ejb.Remote;
import java.io.Serializable;
import java.util.List;

/**
 * Date: 4/25/13
 * Time: 9:34 AM
 *
 * @author
 * @since 1.0
 */
@Remote
public interface IAddressSearch extends Serializable {
    public List<AddressSearchResult> doSearch(String query);
}
