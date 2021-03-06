/*
 * (C) Copyright 2006-2011 Nuxeo SAS (http://nuxeo.com/) and contributors.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser General Public License
 * (LGPL) version 2.1 which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl.html
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * Contributors:
 *     bstefanescu, slacoin
 */
package org.nuxeo.build.maven.filter;

import java.util.List;

import org.apache.maven.artifact.Artifact;
import org.nuxeo.build.maven.MavenClientFactory;
import org.nuxeo.build.maven.graph.Edge;
import org.nuxeo.build.maven.graph.Node;

/**
 * @author <a href="mailto:bs@nuxeo.com">Bogdan Stefanescu</a>
 *
 */
public class AndFilter extends CompositeFilter {

    public AndFilter() {
    }

    public AndFilter(List<Filter> filters) {
        super(filters);
    }

    public boolean accept(Edge edge) {
        for (Filter filter : filters) {
            if (!filter.accept(edge)) {
                if (MavenClientFactory.getLog().isDebugEnabled()) {
                    MavenClientFactory.getLog().debug(
                            "Filtering - " + filter + " refused " + edge);
                }
                return false;
            }
        }
        return true;
    }

    public boolean accept(Artifact artifact) {
        for (Filter filter : filters) {
            if (!filter.accept(artifact)) {
                if (MavenClientFactory.getLog().isDebugEnabled()) {
                    MavenClientFactory.getLog().debug(
                            "Filtering - " + filter + " refused " + artifact);
                }
                return false;
            }
        }
        return true;
    }

    public boolean accept(Node node) {
        for (Filter filter : filters) {
            final boolean accept = filter.accept(node);
            if (!accept) {
                if (MavenClientFactory.getLog().isDebugEnabled()) {
                    MavenClientFactory.getLog().debug(
                            "Filtering - " + filter + " refused " + node);
                }
                return false;
            }
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("" + getClass());
        for (Filter filter : filters) {
            sb.append(System.getProperty("line.separator") + filter);
        }
        return sb.toString();
    }

}
