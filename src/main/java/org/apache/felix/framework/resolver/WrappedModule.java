/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.felix.framework.resolver;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import org.apache.felix.framework.capabilityset.Capability;
import org.apache.felix.framework.capabilityset.Requirement;
import org.apache.felix.framework.util.manifestparser.R4Library;
import org.osgi.framework.Bundle;
import org.osgi.framework.Version;

class WrappedModule implements Module
{
    private final Module m_module;
    private final List<Module> m_fragments;
    private List<Capability> m_cachedCapabilities = null;
    private List<Requirement> m_cachedRequirements = null;

    public WrappedModule(Module module, List<Module> fragments)
    {
        m_module = module;
        m_fragments = fragments;
    }

    public Module getWrappedModule()
    {
        return m_module;
    }

    public List<Module> getFragments()
    {
        return m_fragments;
    }

    public String getId()
    {
        return m_module.getId();
    }

    public List<Capability> getCapabilities()
    {
        if (m_cachedCapabilities == null)
        {
            List<Capability> capList = new ArrayList<Capability>();

            // Wrap host capabilities.
            List<Capability> caps = m_module.getCapabilities();
            for (int capIdx = 0;
                (caps != null) && (capIdx < caps.size());
                capIdx++)
            {
                capList.add(
                    new WrappedCapability(this, caps.get(capIdx)));
            }

            // Wrap fragment capabilities.
            for (int fragIdx = 0;
                (m_fragments != null) && (fragIdx < m_fragments.size());
                fragIdx++)
            {
                caps = m_fragments.get(fragIdx).getCapabilities();
                for (int capIdx = 0;
                    (caps != null) && (capIdx < caps.size());
                    capIdx++)
                {
                    if (caps.get(capIdx).getNamespace().equals(Capability.PACKAGE_NAMESPACE))
                    {
                        capList.add(
                            new WrappedCapability(this, caps.get(capIdx)));
                    }
                }
            }
            m_cachedCapabilities = Collections.unmodifiableList(capList);
        }
        return m_cachedCapabilities;
    }

    public List<Requirement> getRequirements()
    {
        if (m_cachedRequirements == null)
        {
            List<Requirement> reqList = new ArrayList<Requirement>();

            // Wrap host requirements.
            List<Requirement> reqs = m_module.getRequirements();
            for (int reqIdx = 0;
                (reqs != null) && (reqIdx < reqs.size());
                reqIdx++)
            {
                reqList.add(
                    new WrappedRequirement(this, reqs.get(reqIdx)));
            }

            // Wrap fragment requirements.
            for (int fragIdx = 0;
                (m_fragments != null) && (fragIdx < m_fragments.size());
                fragIdx++)
            {
                reqs = m_fragments.get(fragIdx).getRequirements();
                for (int reqIdx = 0;
                    (reqs != null) && (reqIdx < reqs.size());
                    reqIdx++)
                {
                    if (reqs.get(reqIdx).getNamespace().equals(Capability.PACKAGE_NAMESPACE)
                        || reqs.get(reqIdx).getNamespace().equals(Capability.MODULE_NAMESPACE))
                    {
                        reqList.add(
                            new WrappedRequirement(this, reqs.get(reqIdx)));
                    }
                }
            }
            m_cachedRequirements = Collections.unmodifiableList(reqList);
        }
        return m_cachedRequirements;
    }

    public String toString()
    {
        return m_module.getId();
    }

    public Map getHeaders()
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean isExtension()
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String getSymbolicName()
    {
        return m_module.getSymbolicName();
    }

    public Version getVersion()
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public List<Requirement> getDynamicRequirements()
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public List<R4Library> getNativeLibraries()
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public int getDeclaredActivationPolicy()
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Bundle getBundle()
    {
        return m_module.getBundle();
    }

    public List<Wire> getWires()
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean isResolved()
    {
        return false;
    }

    public Object getSecurityContext()
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean isRemovalPending()
    {
        return m_module.isRemovalPending();
    }

    public Content getContent()
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Class getClassByDelegation(String name) throws ClassNotFoundException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public URL getResourceByDelegation(String name)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Enumeration getResourcesByDelegation(String name)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public URL getEntry(String name)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean hasInputStream(int index, String urlPath) throws IOException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public InputStream getInputStream(int index, String urlPath) throws IOException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public URL getLocalURL(int index, String urlPath)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}