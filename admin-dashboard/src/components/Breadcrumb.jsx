import React from 'react';
import { Link } from 'react-router-dom';

const Breadcrumb = ({ paths }) => {
  return (
    <nav aria-label="breadcrumb" className="breadcrumb">
      <ol style={{ listStyle: 'none', padding: 0, display: 'flex', gap: '0.5rem' }}>
        {paths.map((item, index) => (
          <li key={index} style={{ display: 'flex', alignItems: 'center' }}>
            {index < paths.length - 1 ? (
              <Link to={item.link} style={{ textDecoration: 'none', color: '#007bff' }}>
                {item.name}
              </Link>
            ) : (
              <span aria-current="page" style={{ fontWeight: 'bold' }}>
                {item.name}
              </span>
            )}
            {index < paths.length - 1 && <span style={{ margin: '0 0.5rem' }}>›</span>}
          </li>
        ))}
      </ol>
    </nav>
  );
};

export default Breadcrumb;
